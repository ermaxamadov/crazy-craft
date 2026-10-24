package net.minecraft.command;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

/** Travel between the two Crazycraft dimensions and the overworld. */
public class CommandCrazycraft extends CommandBase
{
    @Override
    public String getName()
    {
        return "crazycraft";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "commands.crazycraft.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length != 1)
        {
            throw new WrongUsageException(this.getUsage(sender));
        }

        int dimension;

        if ("old".equalsIgnoreCase(args[0]))
        {
            dimension = 2;
        }
        else if ("future".equalsIgnoreCase(args[0]))
        {
            dimension = 3;
        }
        else if ("home".equalsIgnoreCase(args[0]))
        {
            dimension = 0;
        }
        else
        {
            throw new WrongUsageException(this.getUsage(sender));
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        if (player.dimension != dimension)
        {
            server.getPlayerList().changePlayerDimension(player, dimension);
        }

        sender.sendMessage(new TextComponentTranslation(dimension == 2 ? "commands.crazycraft.old" : dimension == 3 ? "commands.crazycraft.future" : "commands.crazycraft.home"));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, "old", "future", "home") : java.util.Collections.<String>emptyList();
    }
}
