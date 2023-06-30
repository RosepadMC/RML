package net.buj.rml.registry;

import net.buj.rml.Game;
import net.buj.rml.NamespacedKey;
import net.buj.rml.annotations.Nullable;
import net.buj.rml.registry.Materials.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Blocks {
//  public Blocks(Items source) {
//      this.source = source;
//  }

//  private final Items source;

//  public abstract static class Block extends Items.Item {
//      public Material material = Game.MATERIALS.get(new NamespacedKey("minecraft", "unused"));
//      public float slipperiness = 0.6f;
//      public boolean tickOnLoad = false;
//      public boolean opaqueCube = false;
//      public boolean isBlockCoontainer = false;
//      public int lightOpacity = 0;
//      public boolean canBlockGrass = false;
//      public int lightValue = 0;
//      public float hardness = 0;
//      public float resistance = 0;
//      public int blockIndexInTexture = 0;
//      public double minX = 0;
//      public double minY = 0;
//      public double minZ = 0;
//      public double maxX = 1;
//      public double maxY = 1;
//      public double maxZ = 1;
//      public @Nullable Object stepSound = null; // TODO: Replace with StepSound
//      public float blockParticleGravity = 1;

//      public Material getMaterial() {
//          return material;
//      }
//      public Block setMaterial(Material value) {
//          material = value;
//          return this;
//      }

//      public float getSlipperiness() {
//          return slipperiness;
//      }
//      public Block setSlipperiness(float value) {
//          slipperiness = value;
//          return this;
//      }

//      public boolean getTickOnLoad() {
//          return tickOnLoad;
//      }
//      public Block setTickOnLoad(boolean value) {
//          tickOnLoad = value;
//          return this;
//      }

//      public boolean getOpaqueCube() {
//          return opaqueCube;
//      }
//      public Block setOpaqueCube(boolean value) {
//          opaqueCube = value;
//          return this;
//      }

//      public boolean getIsBlockCoontainer() {
//          return isBlockCoontainer;
//      }
//      public Block setIsBlockCoontainer(boolean value) {
//          isBlockCoontainer = value;
//          return this;
//      }

//      public int getLightOpacity() {
//          return lightOpacity;
//      }
//      public Block setLightOpacity(int value) {
//          lightOpacity = value;
//          return this;
//      }

//      public boolean getCanBlockGrass() {
//          return canBlockGrass;
//      }
//      public Block setCanBlockGrass(boolean value) {
//          canBlockGrass = value;
//          return this;
//      }

//      public int getLightValue() {
//          return lightValue;
//      }
//      public Block setLightValue(int value) {
//          lightValue = value;
//          return this;
//      }

//      public float getHardness() {
//          return hardness;
//      }
//      public Block setHardness(float value) {
//          hardness = value;
//          return this;
//      }

//      public float getResistance() {
//          return resistance;
//      }
//      public Block setResistance(float value) {
//          resistance = value;
//          return this;
//      }

//      public int getBlockIndexInTexture() {
//          return blockIndexInTexture;
//      }
//      public Block setBlockIndexInTexture(int value) {
//          blockIndexInTexture = value;
//          return this;
//      }

//      public double getMinX() {
//          return minX;
//      }
//      public Block setMinX(double value) {
//          minX = value;
//          return this;
//      }

//      public double getMinY() {
//          return minY;
//      }
//      public Block setMinY(double value) {
//          minY = value;
//          return this;
//      }

//      public double getMinZ() {
//          return minZ;
//      }
//      public Block setMinZ(double value) {
//          minZ = value;
//          return this;
//      }

//      public double getMaxX() {
//          return maxX;
//      }
//      public Block setMaxX(double value) {
//          maxX = value;
//          return this;
//      }

//      public double getMaxY() {
//          return maxY;
//      }
//      public Block setMaxY(double value) {
//          maxY = value;
//          return this;
//      }

//      public double getMaxZ() {
//          return maxZ;
//      }
//      public Block setMaxZ(double value) {
//          maxZ = value;
//          return this;
//      }

//      public Object getStepSound() {
//          return stepSound;
//      }
//      public Block setStepSound(Object value) {
//          stepSound = value;
//          return this;
//      }

//      public float getBlockParticleGravity() {
//          return blockParticleGravity;
//      }
//      public Block setBlockParticleGravity(float value) {
//          blockParticleGravity = value;
//          return this;
//      }

//  }

//  public int getFreeId() {
//      return source.getFreeId();
//  }

//  public Optional<Blocks.Block> byId(int id) {
//      Optional<Items.Item> item = source.byId(id);
//      if (!item.isPresent()) return Optional.empty();
//      Items.Item item$2 = item.get();
//      if (item$2 instanceof Blocks.Block) return Optional.of((Blocks.Block) item$2);
//      return Optional.empty();
//  }

//  protected abstract @Nullable Blocks.Block createBlock(int id);

//  public @Nullable Blocks.Block create(int id) {
//      if (source.create(id) == null) return null;
//      return createBlock(id);
//  }

//  public @Nullable Blocks.Block free() {
//      return create(getFreeId());
//  }

//  /**
//   * Get all registered items
//   *
//   * @return Blocks list without `null` elements
//   */
//  public List<Blocks.Block> all() {
//      List<Blocks.Block> items = new ArrayList<>();
//      for (Items.Item item : source.all()) {
//          if (item instanceof Blocks.Block) items.add((Block) item);
//      }
//      return items;
//  }
}
