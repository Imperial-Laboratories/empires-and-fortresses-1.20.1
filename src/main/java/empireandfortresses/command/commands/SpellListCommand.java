package empireandfortresses.command.commands;

import com.mojang.brigadier.context.CommandContext;

import empireandfortresses.command.AbstractCommand;
import empireandfortresses.magic.Spells;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class SpellListCommand extends AbstractCommand {

    @Override
    public String getPath() {
        return "spell list";
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(() -> Text.literal("All available spells: " + String.join(", ", Spells.getAllSpellIdsAsString())), false);
        return 1;
    }

}
