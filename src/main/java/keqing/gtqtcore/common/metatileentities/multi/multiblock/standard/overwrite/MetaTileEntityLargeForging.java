package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.overwrite;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTRecipeMapMultiblockOverwrite;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static gregtech.api.GTValues.VA;

public class MetaTileEntityLargeForging extends GTQTRecipeMapMultiblockOverwrite {
    private int casingTier;
    private int tubeTier;
    private int tier;

    public MetaTileEntityLargeForging(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.FORGE_HAMMER_RECIPES);
        this.recipeMapWorkable = new LargeChemicalReactorLogic(this);
    }
    @Override
    public boolean canBeDistinct() {return true;}
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLargeForging(metaTileEntityId);
    }
     int ParallelNum=1;
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("modern", modern);
        data.setInteger("casingTier", casingTier);
        return super.writeToNBT(data);
    }
   
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        modern = data.getInteger("modern");
        casingTier = data.getInteger("casingTier");
    }
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.cracker.gtqtupdate.1"));
        tooltip.add(I18n.format("gregtech.machine.cracker.gtqtupdate.2"));
        tooltip.add(I18n.format("本多方块最高限制配方电压：16 EU"));
    }
    @Override
    public void update() {
        super.update();
        if (modern == 0)
        {
            ParallelNum=ParallelNumA;
        }
        if (modern == 1)
        {
            P = (int) ((this.energyContainer.getEnergyStored() + energyContainer.getInputPerSec())/(getMinVa()==0?1:getMinVa()));
            ParallelNum = Math.min(P, ParallelLim);
        }
    }
    public int getMinVa()
    {
        if((Math.min(this.energyContainer.getEnergyCapacity()/32,VA[tier])*20)==0)return 1;
        return (int)(Math.min(this.energyContainer.getEnergyCapacity()/32,VA[tier]));

    }
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("FFF","XXX", "XXX", "XXX", "FFF")
                .aisle("FPF","XPX", "XPX", "XPX", "FPF")
                .aisle("FFF","XXX", "XSX", "XXX", "FFF")
                .where('S', selfPredicate())
                .where('X', TiredTraceabilityPredicate.CP_CASING.get().setMinGlobalLimited(18)
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2).setPreviewCount(2)))
                .where('P', TiredTraceabilityPredicate.CP_TUBE.get())
                .where('F', states(getFrameState()))
                .where('#', any())
                .build();
    }
    private IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel);
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("gtqtcore.casingTire", casingTier));
        textList.add(new TextComponentTranslation("gtqtcore.tubeTire", tubeTier));
        if(casingTier!=tubeTier)
            textList.add(new TextComponentTranslation("gtqtcore.equal", casingTier,tubeTier));
        if(modern==0) textList.add(new TextComponentTranslation("gtqtcore.tire1",tier));
        if(modern==1) textList.add(new TextComponentTranslation("gtqtcore.tire2",tier));
        textList.add(new TextComponentTranslation("gtqtcore.parr",ParallelNum,ParallelLim));
    }
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        switch (this.casingTier) {
            case (2) -> {
                return Textures.SOLID_STEEL_CASING;
            }
            case (3) -> {
                return Textures.FROST_PROOF_CASING;
            }
            case (4) -> {
                return Textures.CLEAN_STAINLESS_STEEL_CASING;
            }
            case (5) -> {
                return Textures.STABLE_TITANIUM_CASING;
            }
            case (6) -> {
                return Textures.ROBUST_TUNGSTENSTEEL_CASING;
            }
            case (7) -> {
                return GTQTTextures.PD_CASING;
            }
            case (8) -> {
                return GTQTTextures.NQ_CASING;
            }
            case (9) -> {
                return GTQTTextures.ST_CASING;
            }
            case (10) -> {
                return GTQTTextures.AD_CASING;
            }
            default -> {
                return Textures.BRONZE_PLATED_BRICKS;
            }
        }
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object casingTier = context.get("ChemicalPlantCasingTieredStats");
        Object tubeTier = context.get("ChemicalPlantTubeTieredStats");
        this.casingTier = GTQTUtil.getOrDefault(() -> casingTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)casingTier).getIntTier(),
                0);
        this.tubeTier = GTQTUtil.getOrDefault(() -> tubeTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)tubeTier).getIntTier(),
                0);
        this.tier = Math.min(this.casingTier,this.tubeTier);

        this.writeCustomData(GTQTValue.UPDATE_TIER18,buf -> buf.writeInt(this.casingTier));
        ParallelLim=Math.min((int)Math.pow(2, this.tier),32);
        ParallelNum=ParallelLim;
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.casingTier);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.casingTier = buf.readInt();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == GTQTValue.UPDATE_TIER18){
            this.casingTier = buf.readInt();
        }
        if(dataId == GTQTValue.REQUIRE_DATA_UPDATE18){
            this.writeCustomData(GTQTValue.UPDATE_TIER18,buf1 -> buf1.writeInt(this.casingTier));
        }
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }


    @Override
    public String[] getDescription() {
        return new String[]{I18n.format("gtqt.tooltip.update")};
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }

    protected class LargeChemicalReactorLogic extends MultiblockRecipeLogic {


        public LargeChemicalReactorLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity,true);
        }


        public long getMaxVoltage() {
            return 16;
        }
        @Override
        public int getParallelLimit() {
            return ParallelNum;
        }


    }
}