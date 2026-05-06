package empireandfortresses.command;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.minecraft.text.Text;

public class EnumArgumentType<E extends Enum<E>> implements ArgumentType<E> {

    private static final DynamicCommandExceptionType INVALID_ENUM = new DynamicCommandExceptionType(value -> Text.literal("Unknown value: " + value));

    private final Class<E> enumClass;
    private final Collection<String> validValues;

    private EnumArgumentType(Class<E> enumClass) {
        this.enumClass = enumClass;
        this.validValues = Arrays.stream(enumClass.getEnumConstants()).map(e -> e.name().toLowerCase(Locale.ROOT)).toList();
    }

    public static <E extends Enum<E>> EnumArgumentType<E> enumArg(Class<E> enumClass) {
        return new EnumArgumentType<>(enumClass);
    }

    public static <E extends Enum<E>> E getEnum(CommandContext<?> context, String name, Class<E> enumClass) {
        return context.getArgument(name, enumClass);
    }

    @Override
    public E parse(StringReader reader) throws CommandSyntaxException {
        int start = reader.getCursor();
        String word = reader.readUnquotedString();
        try {
            return Enum.valueOf(enumClass, word.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            reader.setCursor(start);
            throw INVALID_ENUM.createWithContext(reader, word);
        }
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        String remaining = builder.getRemaining().toLowerCase(Locale.ROOT);
        for (String value : validValues) {
            if (value.startsWith(remaining)) {
                builder.suggest(value);
            }
        }
        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return validValues;
    }
}