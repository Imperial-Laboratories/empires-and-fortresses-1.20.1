package empireandfortresses.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Predicate;

import com.mojang.brigadier.suggestion.SuggestionProvider;

import static net.minecraft.server.command.CommandManager.literal;

@SuppressWarnings("java:S3038")
public abstract class AbstractCommand implements Command<ServerCommandSource> {

    public abstract String getPath();

    public Predicate<ServerCommandSource> getRequirement() {
        return source -> true;
    }

    @Override
    public abstract int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException;

    protected void configure(LiteralArgumentBuilder<ServerCommandSource> builder) {
        builder.executes(this);
    }

    public LiteralArgumentBuilder<ServerCommandSource> build() {
        String[] parts = getPath().split(" ");

        LiteralArgumentBuilder<ServerCommandSource> tailNode = literal(parts[parts.length - 1]).requires(getRequirement());

        configure(tailNode);

        for (int i = parts.length - 2; i >= 0; i--) {
            tailNode = literal(parts[i]).then(tailNode);
        }

        return tailNode;
    }

    protected <T> T getArgument(CommandContext<ServerCommandSource> context, String name, Class<T> type) {
        return context.getArgument(name, type);
    }

    protected SuggestionProvider<ServerCommandSource> suggest(String... suggestions) {
        return (context, builder) -> CommandSource.suggestMatching(suggestions, builder);
    }

    protected SuggestionProvider<ServerCommandSource> suggest(Identifier... suggestions) {
        return (context, builder) -> CommandSource.suggestIdentifiers(List.of(suggestions), builder);
    }

    protected SuggestionProvider<ServerCommandSource> suggestStrings(List<String> suggestions) {
        return (context, builder) -> CommandSource.suggestMatching(suggestions, builder);
    }

    protected SuggestionProvider<ServerCommandSource> suggestIdentifiers(List<Identifier> suggestions) {
        return (context, builder) -> CommandSource.suggestIdentifiers(suggestions, builder);
    }

    protected ServerPlayerEntity getPlayer(CommandContext<ServerCommandSource> context) {
        return context.getSource().getPlayer();
    }
}
