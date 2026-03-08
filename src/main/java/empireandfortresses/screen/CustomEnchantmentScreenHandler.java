package empireandfortresses.screen;

import empireandfortresses.enchantment.CustomEnchantmentHelper;
import empireandfortresses.enchantment.EnchantingItems;
import empireandfortresses.util.ModTags;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class CustomEnchantmentScreenHandler extends ScreenHandler {
	private final Inventory inventory = new SimpleInventory(2) {
		@Override
		public void markDirty() {
			super.markDirty();
			CustomEnchantmentScreenHandler.this.onContentChanged(this);
		}
	};
	private final ScreenHandlerContext context;
	private final Property seed = Property.create();
	// ! currently no function but I will keep it for the moment
	// public final int[] enchantmentPower = new int[3];
	public final int[] enchantmentId = new int[]{-1, -1, -1};
	public final int[] enchantmentLevel = new int[]{1, 1, 1};
	public int[] enchantmentMaterial = new int[]{1, 1, 1};

	public CustomEnchantmentScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
	}

    public CustomEnchantmentScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModScreenHandlers.ENCHANTING_SCREEN_HANDLER, syncId);
        this.context = context;
        this.addSlot(new Slot(this.inventory, 0, 15, 47) {
			@Override
			public boolean canInsert(ItemStack stack) {
				return true;
			}

		    @Override
		    public int getMaxItemCount() {
		    	return 1;
		    }
        });

        this.addSlot(new Slot(this.inventory, 1, 35, 47){
			@Override
			public boolean canInsert(ItemStack stack) {
				return stack.isIn(ModTags.Items.ENCHANTING_ITEM);
			}
		});

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

		// this.addProperty(Property.create(this.enchantmentPower, 0));
		// this.addProperty(Property.create(this.enchantmentPower, 1));
		// this.addProperty(Property.create(this.enchantmentPower, 2));
		this.addProperty(Property.create(this.enchantmentId, 0));
		this.addProperty(Property.create(this.enchantmentId, 1));
		this.addProperty(Property.create(this.enchantmentId, 2));
		this.addProperty(Property.create(this.enchantmentLevel, 0));
		this.addProperty(Property.create(this.enchantmentLevel, 1));
		this.addProperty(Property.create(this.enchantmentLevel, 2));
		this.addProperty(Property.create(this.enchantmentMaterial, 0));
		this.addProperty(Property.create(this.enchantmentMaterial, 1));
		this.addProperty(Property.create(this.enchantmentMaterial, 2));
    }

	@Override
	public void onContentChanged(Inventory inventory) {
		if (inventory == this.inventory) {
			ItemStack itemStack = inventory.getStack(0);
			ItemStack itemStack2 = inventory.getStack(1);
			if (!itemStack.isEmpty() && itemStack.getItem().isEnchantable(itemStack)) {
				this.context.run((world, pos) -> {

					for (int j = 0; j < 3; j++) {
						// this.enchantmentPower[j] = 0;
						this.enchantmentId[j] = -1;
						this.enchantmentLevel[j] = -1;
						// if (this.enchantmentPower[j] < j + 1) {
						// 	this.enchantmentPower[j] = 0;
						// }
					}

					EnchantmentLevelEntry[] enchantmentLevelEntries = generateEnchantments(itemStack, itemStack2);
					NbtList enchantments = itemStack.getEnchantments();

					for (int jx = 0; jx < 3; jx++) {
						if (enchantmentLevelEntries != null && enchantmentLevelEntries[jx] != null) {
							for (int k = 0; k < enchantments.size(); k++) {
								Identifier enchantmentId = new Identifier(enchantments.getCompound(k).getString("id"));
								Enchantment enchantment = Registries.ENCHANTMENT.get(enchantmentId);

								if (!enchantmentLevelEntries[jx].enchantment.canCombine(enchantment) && enchantmentLevelEntries[jx].enchantment != enchantment) {
									enchantmentLevelEntries[jx] = null;
								}
							}

							if (enchantmentLevelEntries[jx] != null) {
								this.enchantmentId[jx] = Registries.ENCHANTMENT.getRawId(enchantmentLevelEntries[jx].enchantment);
								Enchantment enchantment = Enchantment.byRawId(this.enchantmentId[jx]);

								if (CustomEnchantmentHelper.getNextLevel(itemStack, enchantment) - 1 < enchantment.getMaxLevel()) {
									this.enchantmentLevel[jx] = CustomEnchantmentHelper.getNextLevel(itemStack, enchantment);
									this.enchantmentMaterial[jx] = CustomEnchantmentHelper.materialCost(this.enchantmentLevel[jx]);
								}
							} else {
								this.enchantmentId[jx] = -1;
							}
						} else {
							// this.enchantmentPower[jx] = 0;
							this.enchantmentId[jx] = -1;
							this.enchantmentLevel[jx] = -1;
							this.enchantmentMaterial[jx] = 0;
						}
					}

					this.sendContentUpdates();
				});
			} else {
				for (int i = 0; i < 3; i++) {
					// this.enchantmentPower[i] = 0;
					this.enchantmentId[i] = -1;
					this.enchantmentLevel[i] = 1;
					this.enchantmentMaterial[i] = 1;
				}
			}
		}
	}

	@Override
	public boolean onButtonClick(PlayerEntity player, int id) {
		ItemStack itemStack = this.inventory.getStack(0);
		if (id >= 0 && id < 3) {
			ItemStack itemStack2 = this.inventory.getStack(1);
			if ((itemStack2.isEmpty() || itemStack2.getCount() < this.enchantmentMaterial[id]) && !player.getAbilities().creativeMode) {
				return false;
			} else {
				this.context.run((world, pos) -> {
					ItemStack itemStack3 = itemStack;
					EnchantmentLevelEntry enchantmentLevelEntry = this.generateEnchantment(itemStack, itemStack2, id);
					if (enchantmentLevelEntry != null) {

						if (!CustomEnchantmentHelper.containsEnchantment(itemStack3, enchantmentLevelEntry.enchantment)) {
							itemStack3.addEnchantment(enchantmentLevelEntry.enchantment, this.enchantmentLevel[id]);
						} else {
							NbtList enchantments = itemStack3.getEnchantments();
							int index = CustomEnchantmentHelper.getEnchantmentIndex(itemStack3, enchantmentLevelEntry.enchantment);

							enchantments.getCompound(index).putShort("lvl", (short)this.enchantmentLevel[id]);
						}

						if (!player.getAbilities().creativeMode) {
							itemStack2.decrement(this.enchantmentMaterial[id]);
							if (itemStack2.isEmpty()) {
								this.inventory.setStack(1, ItemStack.EMPTY);
							}
						}

						player.incrementStat(Stats.ENCHANT_ITEM);
						if (player instanceof ServerPlayerEntity) {
							Criteria.ENCHANTED_ITEM.trigger((ServerPlayerEntity)player, itemStack3, this.enchantmentLevel[id]);
						}

						this.inventory.markDirty();
						this.onContentChanged(this.inventory);
						world.playSound(null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);
					}
				});
				return true;
			}
		} else if (id >= 10 && id < 13) {
			int level = this.enchantmentLevel[id - 10];
			Enchantment enchantment = Enchantment.byRawId(this.enchantmentId[id - 10]);
			if (level > 1) {
				this.enchantmentLevel[id - 10] = --level;
				this.enchantmentMaterial[id - 10] = CustomEnchantmentHelper.materialCost(CustomEnchantmentHelper.getNextLevel(itemStack, enchantment) - 1, level);
				this.sendContentUpdates();
			}

			this.context.run((world, pos) -> 
				world.playSound(null, pos, SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F)
			);

			return true;
		} else if (id >= 20 && id < 23) {
			int level = this.enchantmentLevel[id - 20];
			Enchantment enchantment = Enchantment.byRawId(this.enchantmentId[id - 20]);
			if (level < Enchantment.byRawId(this.enchantmentId[id - 20]).getMaxLevel()) {
				this.enchantmentLevel[id - 20] = ++level;
				this.enchantmentMaterial[id - 20] = CustomEnchantmentHelper.materialCost(CustomEnchantmentHelper.getNextLevel(itemStack, enchantment) - 1, level);
				this.sendContentUpdates();
			}

			this.context.run((world, pos) -> 
				world.playSound(null, pos, SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F)
			);

			return true;
		} else {
			Util.error(player.getName() + " pressed invalid button id: " + id);
			return false;
		}
	}

	private EnchantmentLevelEntry generateEnchantment(ItemStack stack, ItemStack stack2, int i) {
		if (!stack2.isEmpty()) {
			Enchantment enchantment = EnchantingItems.enchantingItemMap.get(stack2.getItem()).get(i);
			return enchantment.isAcceptableItem(stack) ? new EnchantmentLevelEntry(enchantment, 1) : null;
		} else {
			return null;
		}
	}

	private EnchantmentLevelEntry[] generateEnchantments(ItemStack stack, ItemStack stack2) {
		EnchantmentLevelEntry[] enchantmentLevelEntries = new EnchantmentLevelEntry[3];
		for (int i = 0; i < 3; i++) {
			if (!stack2.isEmpty()) {
				Enchantment enchantment = EnchantingItems.enchantingItemMap.get(stack2.getItem()).get(i);
				enchantmentLevelEntries[i] = enchantment.isAcceptableItem(stack) ? new EnchantmentLevelEntry(enchantment, 1) : null;
			} else {
				enchantmentLevelEntries = null;
			}
		}
		return enchantmentLevelEntries;
	}

	public int getEnchantingItemCount() {
		ItemStack itemStack = this.inventory.getStack(1);
		return itemStack.isEmpty() ? 0 : itemStack.getCount();
	}

	public int getSeed() {
		return this.seed.get();
	}

	@Override
	public void onClosed(PlayerEntity player) {
		super.onClosed(player);
		this.context.run((world, pos) -> this.dropInventory(player, this.inventory));
	}

    @Override
    public boolean canUse(PlayerEntity player) {
		return canUse(this.context, player, Blocks.ENCHANTING_TABLE);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = (Slot)this.slots.get(slot);
        if (slot2 != null && slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot == 0) {
                if (!this.insertItem(itemStack2, 2, 38, true)) {
                   return ItemStack.EMPTY;
                }
            } else if (slot == 1) {
                if (!this.insertItem(itemStack2, 2, 38, true)) {
                return ItemStack.EMPTY;
                }
            } else if (itemStack2.isIn(ModTags.Items.ENCHANTING_ITEM)) {
                if (!this.insertItem(itemStack2, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (((Slot)this.slots.get(0)).hasStack() || !((Slot)this.slots.get(0)).canInsert(itemStack2)) {
                   return ItemStack.EMPTY;
                }

                ItemStack itemStack3 = itemStack2.copyWithCount(1);
                itemStack2.decrement(1);
                ((Slot)this.slots.get(0)).setStack(itemStack3);
            }

            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot2.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }
}
