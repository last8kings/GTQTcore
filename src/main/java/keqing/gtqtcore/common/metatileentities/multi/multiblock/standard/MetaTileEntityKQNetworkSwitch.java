package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.GTValues;
import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockComputerCasing;
import gregtech.common.blocks.MetaBlocks;

import gregtech.common.metatileentities.multi.electric.MetaTileEntityDataBank;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static gregtech.api.GTValues.VA;

public class MetaTileEntityKQNetworkSwitch extends MetaTileEntityDataBank implements IOpticalComputationProvider {

    int tire;
    private static final int EUT_PER_HATCH = VA[GTValues.IV];

    private final MultipleComputationHandler computationHandler = new MultipleComputationHandler();

    public MetaTileEntityKQNetworkSwitch(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityKQNetworkSwitch(metaTileEntityId);
    }

    @Override
    protected int calculateEnergyUsage() {
        int receivers = getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION).size();
        int transmitters = getAbilities(MultiblockAbility.COMPUTATION_DATA_TRANSMISSION).size();
        return VA[GTValues.LV] * (receivers + transmitters);
    }
    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == GTQTValue.UPDATE_TIER){
            this.tire = buf.readInt();
        }
        if(dataId == GTQTValue.REQUIRE_DATA_UPDATE){
            this.writeCustomData(GTQTValue.UPDATE_TIER,buf1 -> buf1.writeInt(this.tire));
        }
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        computationHandler.onStructureForm(
                getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION),
                getAbilities(MultiblockAbility.COMPUTATION_DATA_TRANSMISSION));
        Object tire = context.get("ChemicalPlantCasingTiredStats");
        this.tire = GTQTUtil.getOrDefault(() -> tire instanceof WrappedIntTired,
                () -> ((WrappedIntTired)tire).getIntTier(),
                0);
        this.writeCustomData(GTQTValue.UPDATE_TIER,buf -> buf.writeInt(this.tire));
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        computationHandler.reset();
    }

    @Override
    protected int getEnergyUsage() {
        return isStructureFormed() ? computationHandler.getEUt() : 0;
    }
    public int vacwu()
    {
        if(tire<=2)return 0;
        return VA[this.tire]*2;
    }
    @Override
    public int requestCWUt(int cwut, boolean simulate,  Collection<IOpticalComputationProvider> seen) {
        seen.add(this);
        if(computationHandler.requestCWUt(cwut, simulate, seen)>vacwu())
        return isActive() && !hasNotEnoughEnergy ? computationHandler.requestCWUt(cwut, simulate, seen) : 0;
        else return 0;
    }

    @Override
    public int getMaxCWUt( Collection<IOpticalComputationProvider> seen) {
        seen.add(this);
        if(computationHandler.getMaxCWUt(seen)>vacwu())
        return isStructureFormed() ? computationHandler.getMaxCWUt(seen) : 0;
        else return 0;
    }

    // allows chaining Network Switches together
    @Override
    public boolean canBridge( Collection<IOpticalComputationProvider> seen) {
        seen.add(this);
        return true;
    }

    @Override
    protected  BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "XAX", "XXX")
                .aisle("XXX", "XSX", "XXX")
                .where('S', selfPredicate())
                .where('A', states(getAdvancedState()))
                .where('X', TiredTraceabilityPredicate.CP_CASING.setMinGlobalLimited(7)
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1, 1))
                        .or(maintenancePredicate())
                        .or(abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION).setMinGlobalLimited(1, 2))
                        .or(abilities(MultiblockAbility.COMPUTATION_DATA_TRANSMISSION).setMinGlobalLimited(1, 1)))
                .build();
    }

    private static  IBlockState getCasingState() {
        return MetaBlocks.COMPUTER_CASING.getState(BlockComputerCasing.CasingType.COMPUTER_CASING);
    }

    private static  IBlockState getAdvancedState() {
        return MetaBlocks.COMPUTER_CASING.getState(BlockComputerCasing.CasingType.ADVANCED_COMPUTER_CASING);
    }

    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        switch (this.tire) {
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

    @SideOnly(Side.CLIENT)
    @Override
    protected  ICubeRenderer getFrontOverlay() {
        return Textures.NETWORK_SWITCH_OVERLAY;
    }

    @Override
    public void addInformation(ItemStack stack, World world,  List<String> tooltip,
                               boolean advanced) {
        tooltip.add(I18n.format("gregtech.machine.network_switch.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.network_switch.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.network_switch.tooltip.3"));
        tooltip.add(I18n.format("gregtech.machine.network_switch.tooltip.4",
                TextFormattingUtil.formatNumbers(EUT_PER_HATCH)));
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .setWorkingStatusKeys(
                        "gregtech.multiblock.idling",
                        "gregtech.multiblock.idling",
                        "gregtech.multiblock.data_bank.providing")
                .addEnergyUsageExactLine(getEnergyUsage())
                .addComputationUsageLine(computationHandler.getMaxCWUtForDisplay())
                .addWorkingStatusLine();
        textList.add(new TextComponentTranslation("gregtech.multiblock.kqns.tier", tire));
        textList.add(new TextComponentTranslation("gregtech.multiblock.kqns.max", computationHandler.getMaxCWUtForDisplay(),vacwu()));
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (isStructureFormed() && computationHandler.hasNonBridgingConnections()) {
            textList.add(TextComponentUtil.translationWithColor(
                    TextFormatting.YELLOW,
                    "gregtech.multiblock.computation.non_bridging.detailed"));
        }
    }

    /** Handles computation load across multiple receivers and to multiple transmitters. */
    private static class MultipleComputationHandler implements IOpticalComputationProvider,
            IOpticalComputationReceiver {

        // providers in the NS provide distributable computation to the NS
        private final Set<IOpticalComputationHatch> providers = new ObjectOpenHashSet<>();
        // transmitters in the NS give computation to other multis
        private final Set<IOpticalComputationHatch> transmitters = new ObjectOpenHashSet<>();

        private int EUt;

        private void onStructureForm(Collection<IOpticalComputationHatch> providers,
                                     Collection<IOpticalComputationHatch> transmitters) {
            reset();
            this.providers.addAll(providers);
            this.transmitters.addAll(transmitters);
            this.EUt = (providers.size() + transmitters.size()) * EUT_PER_HATCH;
        }

        private void reset() {
            providers.clear();
            transmitters.clear();
            EUt = 0;
        }

        @Override
        public int requestCWUt(int cwut, boolean simulate,  Collection<IOpticalComputationProvider> seen) {
            if (seen.contains(this)) return 0;
            // The max CWU/t that this Network Switch can provide, combining all its inputs.
            seen.add(this);
            Collection<IOpticalComputationProvider> bridgeSeen = new ArrayList<>(seen);
            int allocatedCWUt = 0;
            for (var provider : providers) {
                if (!provider.canBridge(bridgeSeen)) continue;
                int allocated = provider.requestCWUt(cwut, simulate, seen);
                allocatedCWUt += allocated;
                cwut -= allocated;
                if (cwut == 0) break;
            }
            return allocatedCWUt;
        }

        public int getMaxCWUtForDisplay() {
            Collection<IOpticalComputationProvider> seen = new ArrayList<>();
            // The max CWU/t that this Network Switch can provide, combining all its inputs.
            seen.add(this);
            Collection<IOpticalComputationProvider> bridgeSeen = new ArrayList<>(seen);
            int maximumCWUt = 0;
            for (var provider : providers) {
                if (!provider.canBridge(bridgeSeen)) continue;
                maximumCWUt += provider.getMaxCWUt(seen);
            }
            return maximumCWUt;
        }

        public int getMaxCWUt( Collection<IOpticalComputationProvider> seen) {
            if (seen.contains(this)) return 0;
            // The max CWU/t that this Network Switch can provide, combining all its inputs.
            seen.add(this);
            Collection<IOpticalComputationProvider> bridgeSeen = new ArrayList<>(seen);
            int maximumCWUt = 0;
            for (var provider : providers) {
                if (!provider.canBridge(bridgeSeen)) continue;
                maximumCWUt += provider.getMaxCWUt(seen);
            }
            return maximumCWUt;
        }

        @Override
        public boolean canBridge( Collection<IOpticalComputationProvider> seen) {
            if (seen.contains(this)) return false;
            seen.add(this);
            for (var provider : providers) {
                if (provider.canBridge(seen)) {
                    return true;
                }
            }
            return false;
        }

        /** The EU/t cost of this Network Switch given the attached providers and transmitters. */
        private int getEUt() {
            return EUt;
        }

        /** Test if any of the provider hatches do not allow bridging */
        private boolean hasNonBridgingConnections() {
            Collection<IOpticalComputationProvider> seen = new ArrayList<>();
            for (var provider : providers) {
                if (!provider.canBridge(seen)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public IOpticalComputationProvider getComputationProvider() {
            return this;
        }
    }
}