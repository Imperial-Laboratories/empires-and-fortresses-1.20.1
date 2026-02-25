package empireandfortresses.event;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class KeyInputHandler {
    // public static KeyBinding magicKey = new KeyBinding("key.magic", 342, "key.categories.gameplay");
    public static KeyBinding magicKey;

    public static void registerKeyInputs() {ClientTickEvents.END_CLIENT_TICK.register(client -> {
	    while (magicKey.wasPressed()) {
	    	if (client.player != null) {
	    	}
	    }
        });
    }

    public static void registerKeyBindings() {
        magicKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.emp_fort.magic", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "key.categories.gameplay"));

        registerKeyInputs();
    }
}
