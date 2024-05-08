package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.unification.material.properties.*;

import static gregicality.multiblocks.api.unification.GCYMMaterials.MaragingSteel300;
import static gregicality.multiblocks.api.unification.GCYMMaterials.Stellite100;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static keqing.gtqtcore.api.unification.material.info.EPMaterialFlags.*;

public class EPMaterialFlagAddition {

    public static void init() {
        //  Coils
        //  Motor coils
        //  Copper (LV), Cupronickel (MV), Electrum (HV), Kanthal (EV),
        //  Graphene (IV), Ruridit (LuV), Vanadium Gallium (ZPM), Americium (UV)
        //  Europium (UHV), Carbon Nanotube (UEV)
        Aluminium.addFlags(GENERATE_ROTOR);
        Copper.addFlags(GENERATE_COIL);
        Cupronickel.addFlags(GENERATE_COIL);
        Electrum.addFlags(GENERATE_COIL);
        Kanthal.addFlags(GENERATE_COIL);
        Graphene.addFlags(GENERATE_COIL);
        Ruridit.addFlags(GENERATE_COIL);
        VanadiumGallium.addFlags(GENERATE_COIL);
        Americium.addFlags(GENERATE_COIL);
        Europium.addFlags(GENERATE_COIL);
        Naquadah.addFlags(GENERATE_FRAME);
        Coal.addFlags(GENERATE_DENSE,GENERATE_PLATE,GENERATE_DOUBLE_PLATE);
        RedSteel.addFlags(GENERATE_BOULE,GENERATE_FRAME);
        // CarbonNanotube.addFlags(GENERATE_COIL);

        //  Voltage coils
        //  Lead (ULV), Steel (LV), Aluminium (MV), Black Steel (HV),
        //  Tungsten Steel (EV), Iridium (IV), Osmiridium (LuV), Europium (ZPM),
        //  Tritanium (UV), Vibranium (UHV), Seaborgium (UEV)
        Lead.addFlags(GENERATE_COIL);
        Steel.addFlags(GENERATE_COIL,GENERATE_DENSE);
        Aluminium.addFlags(GENERATE_COIL);
        BlackSteel.addFlags(GENERATE_COIL);
        TungstenSteel.addFlags(GENERATE_COIL);
        Iridium.addFlags(GENERATE_COIL);
        Osmiridium.addFlags(GENERATE_COIL);
        // Europium.addFlags(GENERATE_COIL);
        Tritanium.addFlags(GENERATE_COIL);
        // Vibranium.addFlags(GENERATE_COIL);
        Seaborgium.addFlags(GENERATE_COIL);

        //  Curved plates

        //  Rotors
        Iron.addFlags(GENERATE_CURVED_PLATE,GENERATE_DENSE);
        WroughtIron.addFlags(GENERATE_CURVED_PLATE);
        Darmstadtium.addFlags(GENERATE_CURVED_PLATE);
        RhodiumPlatedPalladium.addFlags(GENERATE_CURVED_PLATE);
        NaquadahAlloy.addFlags(GENERATE_CURVED_PLATE);
        HSSS.addFlags(GENERATE_CURVED_PLATE);
        //  HastelloyN.addFlags(GENERATE_CURVED_PLATE);
        //  Draconium.addFlags(GENERATE_CURVED_PLATE);
        //  Adamantium.addFlags(GENERATE_CURVED_PLATE);
        //  Dawnstone.addFlags(GENERATE_CURVED_PLATE);

        /*
        *  CEu Vanilla Fluid Pipes
        *  Aluminium
        *  Bronze
        *  Chrome
        *  Copper
        *  Duranium
        *  Europium
        *  Gold
        *  Iridium
        *  Lead
        *  Naquadah
        *  Neutronium
        *  NiobiumTitanium
        *  Polyethylene            (x)
        *  Polybenzimidazole       (x)
        *  Polytetrafluoroethylene (x)
        *  Potin
        *  Stainless Steel
        *  Steel
        *  Tin Alloy
        *  Titanium
        *  Tungsten
        *  Tungsten Carbide
        *  TungstenSteel
        *  VanadiumSteel
         */
        Aluminium.addFlags(GENERATE_CURVED_PLATE);
        Bronze.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_DENSE,GENERATE_SMALL_GEAR);
        Chrome.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        Copper.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_DENSE,GENERATE_SMALL_GEAR);
        Duranium.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        Europium.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        Gold.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        Iridium.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        Lead.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_DENSE,GENERATE_SMALL_GEAR);
        Naquadah.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        Neutronium.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        NiobiumTitanium.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        // Polyethylene.addFlags(GENERATE_CURVED_PLATE);
        // Polybenzimidazole.addFlags(GENERATE_CURVED_PLATE);
        // Polytetrafluoroethylene.addFlags(GENERATE_CURVED_PLATE);
        Potin.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL);
        StainlessSteel.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL);
        Steel.addFlags(GENERATE_CURVED_PLATE);
        TinAlloy.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        Titanium.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        Tungsten.addFlags(GENERATE_CURVED_PLATE);
        TungstenCarbide.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        TungstenSteel.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        VanadiumSteel.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        /*
         *  CEu Vanilla Item Pipes
         *  Americium
         *  Magnalium
         *  Sterling Silver
         *  Tin
         *  Cupronickel
         *  Black Bronze
         *  Cobalt Brass
         *  Electrum
         *  Cobalt
         *  Platinum
         *  Brass
         *  Osmium
         *  Ultimet
         *  Osmiridium
         *  Nickel
         *  Polyvinyl Chloride (x)
         *  Rose Gold
         */
        Americium.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        Magnalium.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        SterlingSilver.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        Tin.addFlags(GENERATE_CURVED_PLATE,GENERATE_DENSE,GENERATE_SMALL_GEAR);
        Cupronickel.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        BlackBronze.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        CobaltBrass.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        Electrum.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        Platinum.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        Brass.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        Osmium.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        Ultimet.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        Osmiridium.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_SMALL_GEAR);
        Nickel.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_DENSE,GENERATE_SMALL_GEAR);
        Cobalt.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_DENSE,GENERATE_SMALL_GEAR);
        // PolyvinylChloride.addFlags(GENERATE_CURVED_PLATE);
        RoseGold.addFlags(GENERATE_CURVED_PLATE,GENERATE_SPRING,GENERATE_SPRING_SMALL);

        //  Disable Crystallization
        Monazite.addFlags(DISABLE_CRYSTALLIZATION);

        //  Crystallizable
        Sapphire.addFlags(CRYSTALLIZABLE);
        Ruby.addFlags(CRYSTALLIZABLE);
        Emerald.addFlags(CRYSTALLIZABLE);
        Olivine.addFlags(CRYSTALLIZABLE);
        Amethyst.addFlags(CRYSTALLIZABLE);
        Opal.addFlags(CRYSTALLIZABLE);
        Platinum.addFlags(GENERATE_ELECTRODE);
        Graphite.addFlags(GENERATE_ELECTRODE);
        Palladium.addFlags(GENERATE_ELECTRODE);
        Silver.addFlags(GENERATE_ELECTRODE);
        Copper.addFlags(GENERATE_ELECTRODE);
        Aluminium.addFlags(GENERATE_ELECTRODE);
        Zinc.addFlags(GENERATE_ELECTRODE);
        Steel.addFlags(GENERATE_ELECTRODE);
        Brass.addFlags(GENERATE_ELECTRODE);

        Carbon.addFlags(GENERATE_NANOTUBE, GENERATE_NANOSENSOR); // W.I.P
        Graphene.addFlags(GENERATE_NANOTUBE, GENERATE_NANOSENSOR); // W.I.P

        Iron.addFlags(GENERATE_NANOTUBE, GENERATE_NANOSENSOR); // W.I.P
        IronMagnetic.addFlags(GENERATE_NANOTUBE, GENERATE_NANOSENSOR); // W.I.P
        Copper.addFlags(GENERATE_NANOTUBE, GENERATE_NANOSENSOR); // W.I.P
        Tin.addFlags(GENERATE_NANOTUBE, GENERATE_NANOSENSOR); // W.I.P
        Nickel.addFlags(GENERATE_NANOTUBE, GENERATE_NANOSENSOR); // W.I.P
        Cobalt.addFlags(GENERATE_NANOTUBE, GENERATE_NANOSENSOR); // W.I.P
        Lead.addFlags(GENERATE_NANOTUBE, GENERATE_NANOSENSOR); // W.I.P
        Redstone.addFlags(GENERATE_NANOTUBE, GENERATE_NANOSENSOR); // W.I.P

        RedAlloy.addFlags(GENERATE_NANOTUBE, GENERATE_NANOSENSOR); // W.I.P

        Silver.addFlags(GENERATE_NANOTUBE, GENERATE_NANOSENSOR); // For PCB factory Etching
        Gold.addFlags(GENERATE_NANOTUBE, GENERATE_NANOSENSOR); // For PCB factory Etching
        Silicon.addFlags(GENERATE_DENSE);
        RedAlloy.addFlags(GENERATE_RING,GENERATE_GEAR);
        Carbon.addFlags(GENERATE_ROD);
        Graphite.setProperty(PropertyKey.FLUID, new FluidProperty());
        Graphite.setProperty(PropertyKey.INGOT, new IngotProperty());
        Magnesium.addFlags(GENERATE_PLATE,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_ROTOR,GENERATE_ROD,GENERATE_SPRING,GENERATE_GEAR,GENERATE_FRAME);
    }
}
