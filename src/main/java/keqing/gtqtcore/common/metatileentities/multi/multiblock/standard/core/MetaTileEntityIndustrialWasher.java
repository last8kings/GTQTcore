package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.core;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.damagesources.DamageSources;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.CubeRendererState;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.cclop.ColourOperation;
import gregtech.client.renderer.cclop.LightMapOperation;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.BloomEffectUtil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.advancement.AdvancementTriggers;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockCore;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTIsaCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

import static gregtech.api.GTValues.IV;
import static gregtech.api.GTValues.VA;
import static keqing.gtqtcore.common.block.blocks.GTQTADVBlock.CasingType.Hdcs;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing.TurbineCasingType.IRIDIUM_CASING;

public class MetaTileEntityIndustrialWasher extends GTQTMultiblockCore {

    public MetaTileEntityIndustrialWasher(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.ORE_WASHER_RECIPES,
                RecipeMaps.CHEMICAL_BATH_RECIPES
        });
    }
    @Override
    public int getMinVa()
    {
        return VA[IV];
    }
    @Override
    public int getCoreNum() {
        return 64;
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityIndustrialWasher(metaTileEntityId);
    }


    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCCCC", "CCCCC", "CCCCC")
                .aisle("CGGGC", "CFFFC", "C###C")
                .aisle("CGGGC", "CFFFC", "C###C")
                .aisle("CGGGC", "CFFFC", "C###C")
                .aisle("PPPPP", "PPSPP", "PPPPP")
                .where('S', this.selfPredicate())
                .where('C', states(getCasingState())
                        .setMinGlobalLimited(28)
                        .or(autoAbilities()))
                .where('G', states(getSecondCasingState()))
                .where('F', states(getFrameState()))
                .where('P', states(getCasingState()))
                .where('#', any())
                .build();
    }
    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(GTQTMaterials.BlackTitanium).getBlock(GTQTMaterials.BlackTitanium);
    }
    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.ADV_BLOCK.getState(Hdcs);
    }

    private static IBlockState getSecondCasingState() {
        return GTQTMetaBlocks.ISA_CASING.getState(GTQTIsaCasing.CasingType.IRIDIUM_TURBINE);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.Hdcs;
    }

    @Override
    public void update() {
        super.update();
    }
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.LARGE_ROCKET_ENGINE_OVERLAY;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(),
                isStructureFormed(), isStructureFormed());
        if (isStructureFormed()) {
            EnumFacing back = getFrontFacing().getOpposite();
            for(float i=-1;i<=1;i++) {
                for (float j = -1; j <=1; j++) {
                    Matrix4 offset = translation.copy().translate(back.getXOffset() * 2+i, 0.6, back.getZOffset() * 2+j);
                    CubeRendererState op = Textures.RENDER_STATE.get();
                    Textures.RENDER_STATE.set(new CubeRendererState(op.layer, CubeRendererState.PASS_MASK, op.world));
                    Textures.renderFace(renderState, offset,
                            ArrayUtils.addAll(pipeline, new LightMapOperation(240, 240), new ColourOperation(0xFF00FFFF)),
                            EnumFacing.UP, Cuboid6.full, TextureUtils.getBlockTexture("water_still"),
                            BloomEffectUtil.getEffectiveBloomLayer());
                    Textures.RENDER_STATE.set(op);
                }
            }
        }
    }
}

