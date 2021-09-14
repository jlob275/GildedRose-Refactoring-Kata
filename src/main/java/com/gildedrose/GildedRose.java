package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            int qualityModifier = -1;
            switch (items[i].name) {
                case "Sulfuras, Hand of Ragnaros":
                    // "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
                    qualityModifier = 0;
                    break;
                
                case "Aged Brie":
                    // "Aged Brie" actually increases in Quality the older it gets
                    qualityModifier = 1;
                    break;

                case "Conjured":
                    // "Conjured" items degrade in Quality twice as fast as normal items
                    qualityModifier *= 2;
                    break;

                case "Backstage passes to a TAFKAL80ETC concert":
                    // "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
	                // Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
	                // Quality drops to 0 after the concert
                    int sellIn = items[i].sellIn;
                    if (sellIn <= 0) {
                        items[i].quality = 0;
                        qualityModifier = 0;
                    } else if (sellIn <= 5) {
                        qualityModifier = 3;
                    } else if (sellIn <= 10) {
                        qualityModifier = 2;
                    } else {
                        qualityModifier = 1;
                    }
                    break;
            }

            // Once the sell by date has passed, Quality degrades twice as fast
            if (items[i].sellIn <= 0) {
                qualityModifier *= 2;
            }

            // enact quality change, but never set quality to more than 50 or less than 0
            if (qualityModifier != 0) {
                items[i].quality += qualityModifier;
                if (items[i].quality > 50) {
                    items[i].quality = 50;
                } else if (items[i].quality < 0) {
                    items[i].quality = 0;
                }
            }

            // Sulfuras never has to be sold
            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                items[i].sellIn = items[i].sellIn - 1;
            }
        }
    }
}