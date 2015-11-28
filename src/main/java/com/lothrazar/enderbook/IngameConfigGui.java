package com.lothrazar.enderbook;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement; 
import net.minecraftforge.fml.client.config.GuiConfig;

public class IngameConfigGui extends GuiConfig 
{
	//thanks to the http://jabelarminecraft.blogspot.ca/p/minecraft-modding-configuration-guis.html
	public IngameConfigGui(GuiScreen parent) 
    {
        super(parent, new ConfigElement(
        		ConfigSettings.config.getCategory(ConfigSettings.category_public)).getChildElements(),
                ModEnderBook.MODID, 
                false, 
                false, 
                "PotionStorage");
    }
	
	@Override
    public void initGui()
    {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        super.actionPerformed(button);
    }
}
