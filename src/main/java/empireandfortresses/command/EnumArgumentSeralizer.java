package empireandfortresses.command;

import com.google.gson.JsonObject;

import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.serialize.ArgumentSerializer;
import net.minecraft.command.argument.serialize.ArgumentSerializer.ArgumentTypeProperties;
import net.minecraft.network.PacketByteBuf;

public class EnumArgumentSeralizer implements ArgumentSerializer<EnumArgumentType<?>, EnumArgumentSeralizer.Properties> {

    @Override
    public void writePacket(Properties properties, PacketByteBuf buf) {
        buf.writeString(properties.enumClassName);
    }

    @Override
    public Properties fromPacket(PacketByteBuf buf) {
        return new Properties(buf.readString());
    }

    @Override
    public void writeJson(Properties properties, JsonObject json) {
        json.addProperty("enum", properties.enumClassName);
    }

    @Override
    public Properties getArgumentTypeProperties(EnumArgumentType<?> argumentType) {
        return new Properties(argumentType.getEnumClass().getName());
    }

    public class Properties implements ArgumentTypeProperties<EnumArgumentType<?>> {

        final String enumClassName;

        Properties(String enumClassName) {
            this.enumClassName = enumClassName;
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public EnumArgumentType<?> createType(CommandRegistryAccess commandRegistryAccess) {
            try {
                Class<?> cls = Class.forName(enumClassName);
                if (!cls.isEnum()) {
                    throw new IllegalStateException(enumClassName + " is not an enum");
                }
                return EnumArgumentType.enumArg((Class) cls);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Unknown enum class: " + enumClassName, e);
            }
        }

        @Override
        public ArgumentSerializer<EnumArgumentType<?>, ?> getSerializer() {
            return EnumArgumentSeralizer.this;
        }
    }
}