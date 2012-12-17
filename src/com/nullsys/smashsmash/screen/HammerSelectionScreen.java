package com.nullsys.smashsmash.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.noobs2d.tweenengine.utils.DynamicButton;
import com.noobs2d.tweenengine.utils.DynamicButton.DynamicButtonCallback;
import com.noobs2d.tweenengine.utils.DynamicDisplay.DynamicRegistration;
import com.noobs2d.tweenengine.utils.DynamicDisplayGroup;
import com.noobs2d.tweenengine.utils.DynamicScreen;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.noobs2d.tweenengine.utils.DynamicText;
import com.nullsys.smashsmash.Art;
import com.nullsys.smashsmash.Fonts;
import com.nullsys.smashsmash.Settings;
import com.nullsys.smashsmash.hammer.Hammer;
import com.nullsys.smashsmash.hammer.Hammer.Descriptions;

public class HammerSelectionScreen extends DynamicScreen implements GestureListener, DynamicButtonCallback {

    private DynamicSprite test;
    private ArrayList<DynamicButton> buttons = new ArrayList<DynamicButton>();
    private DynamicDisplayGroup woodenHammerPane = new DynamicDisplayGroup();
    private DynamicDisplayGroup metalHeadPane = new DynamicDisplayGroup();
    private DynamicDisplayGroup fierySmashPane = new DynamicDisplayGroup();
    private DynamicDisplayGroup frostBitePane = new DynamicDisplayGroup();
    private DynamicDisplayGroup duskPane = new DynamicDisplayGroup();
    private DynamicDisplayGroup twilightPane = new DynamicDisplayGroup();
    private DynamicDisplayGroup starryNightPane = new DynamicDisplayGroup();
    private DynamicDisplayGroup spaceBlasterPane = new DynamicDisplayGroup();
    private DynamicDisplayGroup bubblesPane = new DynamicDisplayGroup();

    private float velX, velY;

    private boolean flinging = false;

    private boolean panning = false;

    private DynamicDisplayGroup mjolnirPane = new DynamicDisplayGroup();

    public HammerSelectionScreen(Game game) {
	super(game, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
	test = new DynamicSprite(Art.pukes.findRegion("PUKE_GREEN"), Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
	initPanels();
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
	//	System.out.println("FLING");
	flinging = true;
	velX = velocityX * 0.5f;
	velY = velocityY * 0.5f;
	return false;
    }

    @Override
    public boolean longPress(float x, float y) {
	return false;
    }

    @Override
    public void onButtonEvent(DynamicButton button, int eventType) {
	if (eventType == UP)
	    if (button.getName().equals("METALHEAD-BUY")) {
		if (Settings.buyHammer(Hammer.METAL_HEAD)) {
		    metalHeadPane.removeByName("METALHEAD-BUY");
		    metalHeadPane.removeByName("PRICE");
		    removeButtonByName("METALHEAD-BUY");
		    addMetalHeadEquipButton();
		    // TODO show purchase successful
		} else {
		    // TODO show insufficient balance watever
		}
	    } else if (button.getName().equals("FIERY-SMASH-BUY")) {
		if (Settings.buyHammer(Hammer.FIERY_SMASH)) {
		    fierySmashPane.removeByName("FIERY-SMASH-BUY");
		    fierySmashPane.removeByName("PRICE");
		    removeButtonByName("FIERY-SMASH-BUY");
		    addFierySmashEquipButton();
		    // TODO show purchase successful
		} else {
		    // TODO show insufficient balance watever
		}
	    } else if (button.getName().equals("FROST-BITE-BUY")) {
		if (Settings.buyHammer(Hammer.FROST_BITE)) {
		    frostBitePane.removeByName("FROST-BITE-BUY");
		    frostBitePane.removeByName("PRICE");
		    removeButtonByName("FROST-BITE-BUY");
		    addFrostBiteEquipButton();
		    // TODO show purchase successful
		} else {
		    // TODO show insufficient balance watever
		}
	    } else if (button.getName().equals("DUSK-BUY")) {
		if (Settings.buyHammer(Hammer.DUSK)) {
		    duskPane.removeByName("DUSK-BUY");
		    duskPane.removeByName("PRICE");
		    removeButtonByName("DUSK-BUY");
		    addDuskEquipButton();
		    // TODO show purchase successful
		} else {
		    // TODO show insufficient balance watever
		}
	    } else if (button.getName().equals("TWILIGHT-BUY")) {
		if (Settings.buyHammer(Hammer.TWILIGHT)) {
		    twilightPane.removeByName("TWILIGHT-BUY");
		    twilightPane.removeByName("PRICE");
		    removeButtonByName("TWILIGHT-BUY");
		    addTwilightEquipButton();
		    // TODO show purchase successful
		} else {
		    // TODO show insufficient balance watever
		}
	    } else if (button.getName().equals("STARRY-NIGHT-BUY")) {
		if (Settings.buyHammer(Hammer.STARRY_NIGHT)) {
		    starryNightPane.removeByName("STARRY-NIGHT-BUY");
		    starryNightPane.removeByName("PRICE");
		    removeButtonByName("STARRY-NIGHT-BUY");
		    addStarryNightEquipButton();
		    // TODO show purchase successful
		} else {
		    // TODO show insufficient balance watever
		}
	    } else if (button.getName().equals("SPACE-BLASTER-BUY")) {
		if (Settings.buyHammer(Hammer.SPACE_BLASTER)) {
		    spaceBlasterPane.removeByName("SPACE-BLASTER-BUY");
		    spaceBlasterPane.removeByName("PRICE");
		    removeButtonByName("SPACE-BLASTER-BUY");
		    addSpaceBlasterEquipButton();
		    // TODO show purchase successful
		} else {
		    // TODO show insufficient balance watever
		}
	    } else if (button.getName().equals("BUBBLES-BUY")) {
		if (Settings.buyHammer(Hammer.BUBBLES)) {
		    bubblesPane.removeByName("BUBBLES-BUY");
		    bubblesPane.removeByName("PRICE");
		    removeButtonByName("BUBBLES-BUY-BUY");
		    addBubblesEquipButton();
		    // TODO show purchase successful
		} else {
		    // TODO show insufficient balance watever
		}
	    } else if (button.getName().equals("METALHEAD-EQUIP")) {

	    } else if (button.getName().equals("FIERY-SMASH-EQUIP")) {

	    } else if (button.getName().equals("FROST-BITE-EQUIP")) {

	    } else if (button.getName().equals("DUSK-EQUIP")) {

	    } else if (button.getName().equals("TWILIGHT-EQUIP")) {

	    } else if (button.getName().equals("STARRY-NIGHT-EQUIP")) {

	    } else if (button.getName().equals("SPACE-BLASTER-EQUIP")) {

	    } else if (button.getName().equals("BUBBLES-EQUIP")) {

	    } else if (button.getName().equals("WOODEN-HAMMER-EQUIP")) {

	    }
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
	//	System.out.println("PAN");
	x *= (float) Settings.SCREEN_WIDTH / Gdx.graphics.getWidth();
	y = (Gdx.graphics.getHeight() * camera.zoom - y) * Settings.SCREEN_HEIGHT / Gdx.graphics.getHeight();

	panning = true;
	flinging = false;
	//	if (test.getY() <= 600 || test.getY() >= 300)
	//	test.setY(test.getY() - deltaY);
	woodenHammerPane.setY(woodenHammerPane.getY() - deltaY);
	metalHeadPane.setY(metalHeadPane.getY() - deltaY);
	fierySmashPane.setY(fierySmashPane.getY() - deltaY);
	frostBitePane.setY(frostBitePane.getY() - deltaY);
	duskPane.setY(duskPane.getY() - deltaY);
	twilightPane.setY(twilightPane.getY() - deltaY);
	starryNightPane.setY(starryNightPane.getY() - deltaY);
	spaceBlasterPane.setY(spaceBlasterPane.getY() - deltaY);
	bubblesPane.setY(bubblesPane.getY() - deltaY);

	return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
	return false;
    }

    @Override
    public void render(float delta) {
	super.render(delta);

	Gdx.gl10.glClearColor(0.25f, 1f, 0.25f, 1f);

	spriteBatch.begin();
	test.render(spriteBatch);
	test.update(delta);
	duskPane.render(spriteBatch);
	fierySmashPane.render(spriteBatch);
	frostBitePane.render(spriteBatch);
	woodenHammerPane.render(spriteBatch);
	metalHeadPane.render(spriteBatch);
	twilightPane.render(spriteBatch);
	starryNightPane.render(spriteBatch);
	spaceBlasterPane.render(spriteBatch);
	bubblesPane.render(spriteBatch);

	duskPane.update(delta);
	fierySmashPane.update(delta);
	frostBitePane.update(delta);
	metalHeadPane.update(delta);
	woodenHammerPane.update(delta);
	twilightPane.update(delta);
	starryNightPane.update(delta);
	spaceBlasterPane.update(delta);
	bubblesPane.update(delta);
	spriteBatch.end();

	if (flinging) {
	    //	    System.out.println("FLINGING ~ " + test.getY());
	    velX *= 0.98f;
	    velY *= 0.98f;
	    //	    float increase = test.getY() - velY * delta;
	    //	    if (test.getY() + increase <= 800 && test.getY() - increase >= 00)
	    //	    test.setY(increase);
	    woodenHammerPane.setY(woodenHammerPane.getY() - velY * delta);
	    metalHeadPane.setY(metalHeadPane.getY() - velY * delta);
	    fierySmashPane.setY(fierySmashPane.getY() - velY * delta);
	    frostBitePane.setY(frostBitePane.getY() - velY * delta);
	    duskPane.setY(duskPane.getY() - velY * delta);
	    twilightPane.setY(twilightPane.getY() - velY * delta);
	    starryNightPane.setY(starryNightPane.getY() - velY * delta);
	    spaceBlasterPane.setY(spaceBlasterPane.getY() - velY * delta);
	    bubblesPane.setY(bubblesPane.getY() - velY * delta);
	    if (Math.abs(velX) < 0.01f)
		velX = 0;
	    if (Math.abs(velY) < 0.01f)
		velY = 0;
	}

	//	if (!panning && test.getY() >= 800) {
	//	    flinging = false;
	//	    test.setY(test.getY() - 10f);
	//	} else if (!panning && test.getY() < 0) {
	//	    flinging = false;
	//	    test.setY(test.getY() + 10f);
	//	}

	if (!panning && woodenHammerPane.getY() >= 1650) {
	    flinging = false;
	    woodenHammerPane.setY(woodenHammerPane.getY() - 10f);
	    metalHeadPane.setY(metalHeadPane.getY() - 10f);
	    fierySmashPane.setY(fierySmashPane.getY() - 10f);
	    frostBitePane.setY(frostBitePane.getY() - 10f);
	    duskPane.setY(duskPane.getY() - 10f);
	    twilightPane.setY(twilightPane.getY() - 10f);
	    starryNightPane.setY(starryNightPane.getY() - 10f);
	    spaceBlasterPane.setY(spaceBlasterPane.getY() - 10f);
	    bubblesPane.setY(bubblesPane.getY() - 10f);
	} else if (!panning && woodenHammerPane.getY() <= 594) {
	    flinging = false;
	    woodenHammerPane.setY(woodenHammerPane.getY() + 10f);
	    metalHeadPane.setY(metalHeadPane.getY() + 10f);
	    fierySmashPane.setY(fierySmashPane.getY() + 10f);
	    frostBitePane.setY(frostBitePane.getY() + 10f);
	    duskPane.setY(duskPane.getY() + 10f);
	    twilightPane.setY(twilightPane.getY() + 10f);
	    starryNightPane.setY(starryNightPane.getY() + 10f);
	    spaceBlasterPane.setY(spaceBlasterPane.getY() + 10f);
	    bubblesPane.setY(bubblesPane.getY() + 10f);
	}
    }

    @Override
    public void show() {
	Gdx.input.setInputProcessor(new GestureDetector(20, 0.5f, 2, 1f, this) {

	    @Override
	    public boolean touchUp(float x, float y, int pointer, int button) {
		super.touchUp(x, y, pointer, button);
		System.out.println("UP");
		x *= (float) Settings.SCREEN_WIDTH / Gdx.graphics.getWidth();
		y = (Gdx.graphics.getHeight() * camera.zoom - y) * Settings.SCREEN_HEIGHT / Gdx.graphics.getHeight();
		for (int i = 0; i < buttons.size(); i++)
		    buttons.get(i).inputUp(x, y);
		panning = false;
		return true;
	    }

	});
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
	System.out.println("TAP");
	panning = false;
	x *= (float) Settings.SCREEN_WIDTH / Gdx.graphics.getWidth();
	y = (Gdx.graphics.getHeight() * camera.zoom - y) * Settings.SCREEN_HEIGHT / Gdx.graphics.getHeight();
	return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
	System.out.println("DOWN");
	x *= (float) Settings.SCREEN_WIDTH / Gdx.graphics.getWidth();
	y = (Gdx.graphics.getHeight() * camera.zoom - y) * Settings.SCREEN_HEIGHT / Gdx.graphics.getHeight();
	for (int i = 0; i < buttons.size(); i++)
	    buttons.get(i).inputDown(x, y);
	flinging = false;
	return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
	System.out.println("ZOOM");
	return false;
    }

    private void addBubblesEquipButton() {
	DynamicSprite soldOut = new DynamicSprite(Art.hammers.findRegion("SOLD-OUT"), 181, 35);
	soldOut.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	bubblesPane.add(soldOut);

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	downstate = Art.hammers.findRegion("EQUIP-DOWNSTATE");
	hoverstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	DynamicButton equip = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

	    @Override
	    public void inputDown(float x, float y) {
		super.inputDown(x - bubblesPane.getX(), y - bubblesPane.getY());
	    }

	    @Override
	    public void inputUp(float x, float y) {
		super.inputUp(x - bubblesPane.getX(), y - bubblesPane.getY());
	    }

	};
	equip.setName("BUBBLES-EQUIP");
	equip.setCallback(this);
	buttons.add(equip);
	bubblesPane.add(equip);
    }

    private void addDuskEquipButton() {
	DynamicSprite soldOut = new DynamicSprite(Art.hammers.findRegion("SOLD-OUT"), 181, 35);
	soldOut.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	duskPane.add(soldOut);

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	downstate = Art.hammers.findRegion("EQUIP-DOWNSTATE");
	hoverstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	DynamicButton equip = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

	    @Override
	    public void inputDown(float x, float y) {
		super.inputDown(x - duskPane.getX(), y - duskPane.getY());
	    }

	    @Override
	    public void inputUp(float x, float y) {
		super.inputUp(x - duskPane.getX(), y - duskPane.getY());
	    }

	};
	equip.setName("DUSK-EQUIP");
	equip.setCallback(this);
	buttons.add(equip);
	duskPane.add(equip);
    }

    private void addFierySmashEquipButton() {
	DynamicSprite soldOut = new DynamicSprite(Art.hammers.findRegion("SOLD-OUT"), 181, 35);
	soldOut.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	fierySmashPane.add(soldOut);

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	downstate = Art.hammers.findRegion("EQUIP-DOWNSTATE");
	hoverstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	DynamicButton equip = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

	    @Override
	    public void inputDown(float x, float y) {
		super.inputDown(x - fierySmashPane.getX(), y - fierySmashPane.getY());
	    }

	    @Override
	    public void inputUp(float x, float y) {
		super.inputUp(x - fierySmashPane.getX(), y - fierySmashPane.getY());
	    }

	};
	equip.setName("FIERY-SMASH-EQUIP");
	equip.setCallback(this);
	buttons.add(equip);
	fierySmashPane.add(equip);
    }

    private void addFrostBiteEquipButton() {
	DynamicSprite soldOut = new DynamicSprite(Art.hammers.findRegion("SOLD-OUT"), 181, 35);
	soldOut.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	frostBitePane.add(soldOut);

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	downstate = Art.hammers.findRegion("EQUIP-DOWNSTATE");
	hoverstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	DynamicButton equip = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

	    @Override
	    public void inputDown(float x, float y) {
		super.inputDown(x - frostBitePane.getX(), y - frostBitePane.getY());
	    }

	    @Override
	    public void inputUp(float x, float y) {
		super.inputUp(x - frostBitePane.getX(), y - frostBitePane.getY());
	    }

	};
	equip.setName("FROST-EQUIP");
	equip.setCallback(this);
	buttons.add(equip);
	frostBitePane.add(equip);
    }

    private void addMetalHeadEquipButton() {
	DynamicSprite soldOut = new DynamicSprite(Art.hammers.findRegion("SOLD-OUT"), 181, 35);
	soldOut.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	metalHeadPane.add(soldOut);

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	downstate = Art.hammers.findRegion("EQUIP-DOWNSTATE");
	hoverstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	DynamicButton equip = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

	    @Override
	    public void inputDown(float x, float y) {
		super.inputDown(x - metalHeadPane.getX(), y - metalHeadPane.getY());
	    }

	    @Override
	    public void inputUp(float x, float y) {
		super.inputUp(x - metalHeadPane.getX(), y - metalHeadPane.getY());
	    }

	};
	equip.setName("METALHEAD-EQUIP");
	equip.setCallback(this);
	buttons.add(equip);
	metalHeadPane.add(equip);
    }

    private void addPaneComponents(DynamicDisplayGroup group, String title, String description) {
	DynamicSprite pane = new DynamicSprite(Art.hammers.findRegion("PANE"), 0, 0);
	pane.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	group.add(pane);
	DynamicSprite itemPane = new DynamicSprite(Art.hammers.findRegion("ITEM-PANE"), 100, 98);
	group.add(itemPane);

	DynamicText itemTitle = new DynamicText(Fonts.bdCartoonShoutx50GrayStroke, title);
	itemTitle.setRegistration(DynamicRegistration.CENTER_LEFT);
	itemTitle.setPosition(181, 143);
	//	title.setScale(1.64f);
	group.add(itemTitle);
	DynamicText itemDescription = new DynamicText(Fonts.bdCartoonShoutx50GrayStroke, description);
	itemDescription.setRegistration(DynamicRegistration.CENTER_LEFT);
	itemDescription.setPosition(181, 107);
	itemDescription.setScale(.64f);
	group.add(itemDescription);
    }

    private void addSpaceBlasterEquipButton() {
	DynamicSprite soldOut = new DynamicSprite(Art.hammers.findRegion("SOLD-OUT"), 181, 35);
	soldOut.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	spaceBlasterPane.add(soldOut);

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	downstate = Art.hammers.findRegion("EQUIP-DOWNSTATE");
	hoverstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	DynamicButton equip = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

	    @Override
	    public void inputDown(float x, float y) {
		super.inputDown(x - spaceBlasterPane.getX(), y - spaceBlasterPane.getY());
	    }

	    @Override
	    public void inputUp(float x, float y) {
		super.inputUp(x - spaceBlasterPane.getX(), y - spaceBlasterPane.getY());
	    }

	};
	equip.setName("SPACE-BLASTER-EQUIP");
	equip.setCallback(this);
	buttons.add(equip);
	spaceBlasterPane.add(equip);
    }

    private void addStarryNightEquipButton() {
	DynamicSprite soldOut = new DynamicSprite(Art.hammers.findRegion("SOLD-OUT"), 181, 35);
	soldOut.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	starryNightPane.add(soldOut);

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	downstate = Art.hammers.findRegion("EQUIP-DOWNSTATE");
	hoverstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	DynamicButton equip = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

	    @Override
	    public void inputDown(float x, float y) {
		super.inputDown(x - starryNightPane.getX(), y - starryNightPane.getY());
	    }

	    @Override
	    public void inputUp(float x, float y) {
		super.inputUp(x - starryNightPane.getX(), y - starryNightPane.getY());
	    }

	};
	equip.setName("STARRY-NIGHT-EQUIP");
	equip.setCallback(this);
	buttons.add(equip);
	starryNightPane.add(equip);
    }

    private void addTwilightEquipButton() {
	DynamicSprite soldOut = new DynamicSprite(Art.hammers.findRegion("SOLD-OUT"), 181, 35);
	soldOut.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	twilightPane.add(soldOut);

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	downstate = Art.hammers.findRegion("EQUIP-DOWNSTATE");
	hoverstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	DynamicButton equip = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

	    @Override
	    public void inputDown(float x, float y) {
		super.inputDown(x - twilightPane.getX(), y - twilightPane.getY());
	    }

	    @Override
	    public void inputUp(float x, float y) {
		super.inputUp(x - twilightPane.getX(), y - twilightPane.getY());
	    }

	};
	equip.setName("TWILIGHT-EQUIP");
	equip.setCallback(this);
	buttons.add(equip);
	twilightPane.add(equip);
    }

    private void addWoodenHammerEquipButton() {
	DynamicSprite soldOut = new DynamicSprite(Art.hammers.findRegion("SOLD-OUT"), 181, 35);
	soldOut.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	woodenHammerPane.add(soldOut);

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	downstate = Art.hammers.findRegion("EQUIP-DOWNSTATE");
	hoverstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	DynamicButton equip = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

	    @Override
	    public void inputDown(float x, float y) {
		super.inputDown(x - woodenHammerPane.getX(), y - woodenHammerPane.getY());
	    }

	    @Override
	    public void inputUp(float x, float y) {
		super.inputUp(x - woodenHammerPane.getX(), y - woodenHammerPane.getY());
	    }

	};
	equip.setName("WOODEN-HAMMER-EQUIP");
	equip.setCallback(this);
	buttons.add(equip);
	woodenHammerPane.add(equip);
    }

    private void initBubblesPane() {
	addPaneComponents(bubblesPane, "BUBBLES", Descriptions.BUBBLES);
	TextureRegion upstate, downstate, hoverstate;
	if (Settings.hasBubbles && Settings.hammerType != Hammer.BUBBLES)
	    addBubblesEquipButton();
	else if (!Settings.hasBubbles) {
	    upstate = Art.hammers.findRegion("BUY-UPSTATE");
	    downstate = Art.hammers.findRegion("BUY-DOWNSTATE");
	    hoverstate = Art.hammers.findRegion("BUY-UPSTATE");
	    DynamicButton buy = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

		@Override
		public void inputDown(float x, float y) {
		    super.inputDown(x - bubblesPane.getX(), y - bubblesPane.getY());
		}

		@Override
		public void inputUp(float x, float y) {
		    super.inputUp(x - bubblesPane.getX(), y - bubblesPane.getY());
		}
	    };
	    buy.setName("BUBBLES-BUY");
	    buy.setCallback(this);
	    buttons.add(buy);
	    bubblesPane.add(buy);

	    DynamicText price = new DynamicText(Fonts.berlinSansx32OrangeStroke, "$ 35,000");
	    price.setRegistration(DynamicRegistration.CENTER_LEFT);
	    price.setPosition(181, 60);
	    price.setScale(.6f);
	    price.setName("PRICE");
	    bubblesPane.add(price);
	}
	bubblesPane.setX(129);
	bubblesPane.setY(-1046);
    }

    private void initDuskPane() {
	addPaneComponents(duskPane, "DUSK", Descriptions.DUSK);
	TextureRegion upstate, downstate, hoverstate;
	if (Settings.hasDusk && Settings.hammerType != Hammer.DUSK)
	    addDuskEquipButton();
	else if (!Settings.hasDusk) {
	    upstate = Art.hammers.findRegion("BUY-UPSTATE");
	    downstate = Art.hammers.findRegion("BUY-DOWNSTATE");
	    hoverstate = Art.hammers.findRegion("BUY-UPSTATE");
	    DynamicButton buy = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

		@Override
		public void inputDown(float x, float y) {
		    super.inputDown(x - duskPane.getX(), y - duskPane.getY());
		}

		@Override
		public void inputUp(float x, float y) {
		    super.inputUp(x - duskPane.getX(), y - duskPane.getY());
		}
	    };
	    buy.setName("DUSK-BUY");
	    buy.setCallback(this);
	    buttons.add(buy);
	    duskPane.add(buy);

	    DynamicText price = new DynamicText(Fonts.berlinSansx32OrangeStroke, "$ 25,000");
	    price.setRegistration(DynamicRegistration.CENTER_LEFT);
	    price.setPosition(181, 60);
	    price.setScale(.6f);
	    price.setName("PRICE");
	    duskPane.add(price);
	}
	duskPane.setX(129);
	duskPane.setY(-226);
    }

    private void initFierySmashPane() {
	addPaneComponents(fierySmashPane, "FIERY SMASH", Descriptions.FIERY_SMASH);
	TextureRegion upstate, downstate, hoverstate;
	if (Settings.hasFierySmash && Settings.hammerType != Hammer.FIERY_SMASH)
	    addFierySmashEquipButton();
	else if (!Settings.hasFierySmash) {
	    upstate = Art.hammers.findRegion("BUY-UPSTATE");
	    downstate = Art.hammers.findRegion("BUY-DOWNSTATE");
	    hoverstate = Art.hammers.findRegion("BUY-UPSTATE");
	    DynamicButton buy = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

		@Override
		public void inputDown(float x, float y) {
		    super.inputDown(x - fierySmashPane.getX(), y - fierySmashPane.getY());
		}

		@Override
		public void inputUp(float x, float y) {
		    super.inputUp(x - fierySmashPane.getX(), y - fierySmashPane.getY());
		}
	    };
	    buy.setName("FIERY-SMASH-BUY");
	    buy.setCallback(this);
	    buttons.add(buy);
	    fierySmashPane.add(buy);

	    DynamicText price = new DynamicText(Fonts.berlinSansx32OrangeStroke, "$ 15,000");
	    price.setRegistration(DynamicRegistration.CENTER_LEFT);
	    price.setPosition(181, 60);
	    price.setScale(.6f);
	    price.setName("PRICE");
	    fierySmashPane.add(price);
	}
	fierySmashPane.setX(129);
	fierySmashPane.setY(184);
    }

    private void initFrostBitePane() {
	addPaneComponents(frostBitePane, "FROST BITE", Descriptions.FROST_BITE);
	TextureRegion upstate, downstate, hoverstate;
	if (Settings.hasFrostBite && Settings.hammerType != Hammer.FROST_BITE)
	    addFrostBiteEquipButton();
	else if (!Settings.hasFrostBite) {
	    upstate = Art.hammers.findRegion("BUY-UPSTATE");
	    downstate = Art.hammers.findRegion("BUY-DOWNSTATE");
	    hoverstate = Art.hammers.findRegion("BUY-UPSTATE");
	    DynamicButton buy = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

		@Override
		public void inputDown(float x, float y) {
		    super.inputDown(x - frostBitePane.getX(), y - frostBitePane.getY());
		}

		@Override
		public void inputUp(float x, float y) {
		    super.inputUp(x - frostBitePane.getX(), y - frostBitePane.getY());
		}
	    };
	    buy.setName("FROST-BITE-BUY");
	    buy.setCallback(this);
	    buttons.add(buy);
	    frostBitePane.add(buy);

	    DynamicText price = new DynamicText(Fonts.berlinSansx32OrangeStroke, "$ 15,000");
	    price.setRegistration(DynamicRegistration.CENTER_LEFT);
	    price.setPosition(181, 60);
	    price.setScale(.6f);
	    price.setName("PRICE");
	    frostBitePane.add(price);
	}
	frostBitePane.setX(129);
	frostBitePane.setY(-21);
    }

    private void initMetalheadPane() {
	addPaneComponents(metalHeadPane, "METALHEAD", Descriptions.METAL_HEAD);
	TextureRegion upstate, downstate, hoverstate;
	if (Settings.hasMetalhead && Settings.hammerType != Hammer.METAL_HEAD)
	    addMetalHeadEquipButton();
	else if (!Settings.hasMetalhead) {
	    DynamicText price = new DynamicText(Fonts.berlinSansx32OrangeStroke, "$ 10,000");
	    price.setRegistration(DynamicRegistration.CENTER_LEFT);
	    price.setPosition(181, 60);
	    price.setScale(.6f);
	    price.setName("PRICE");
	    metalHeadPane.add(price);

	    upstate = Art.hammers.findRegion("BUY-UPSTATE");
	    downstate = Art.hammers.findRegion("BUY-DOWNSTATE");
	    hoverstate = Art.hammers.findRegion("BUY-UPSTATE");
	    DynamicButton buy = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

		@Override
		public void inputDown(float x, float y) {
		    super.inputDown(x - metalHeadPane.getX(), y - metalHeadPane.getY());
		}

		@Override
		public void inputUp(float x, float y) {
		    super.inputUp(x - metalHeadPane.getX(), y - metalHeadPane.getY());
		}
	    };
	    buy.setName("METALHEAD-BUY");
	    buy.setCallback(this);
	    buttons.add(buy);
	    metalHeadPane.add(buy);
	}
	metalHeadPane.setX(129);
	metalHeadPane.setY(389);
    }

    private void initMjolnirPane() {
	addPaneComponents(mjolnirPane, "MJOLNIR", Descriptions.MJOLNIR);
	TextureRegion upstate, downstate, hoverstate;
	if (Settings.hasMjolnir && Settings.hammerType != Hammer.MJOLNIR)
	    addSpaceBlasterEquipButton();
	else if (!Settings.hasSpaceBlaster) {
	    upstate = Art.hammers.findRegion("BUY-UPSTATE");
	    downstate = Art.hammers.findRegion("BUY-DOWNSTATE");
	    hoverstate = Art.hammers.findRegion("BUY-UPSTATE");
	    DynamicButton buy = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

		@Override
		public void inputDown(float x, float y) {
		    super.inputDown(x - mjolnirPane.getX(), y - mjolnirPane.getY());
		}

		@Override
		public void inputUp(float x, float y) {
		    super.inputUp(x - mjolnirPane.getX(), y - mjolnirPane.getY());
		}
	    };
	    buy.setName("MJOLNIR-BUY");
	    buy.setCallback(this);
	    buttons.add(buy);
	    mjolnirPane.add(buy);

	    DynamicText price = new DynamicText(Fonts.berlinSansx32OrangeStroke, "$ 99,999");
	    price.setRegistration(DynamicRegistration.CENTER_LEFT);
	    price.setPosition(181, 60);
	    price.setScale(.6f);
	    price.setName("PRICE");
	    mjolnirPane.add(price);
	}
	mjolnirPane.setX(129);
	mjolnirPane.setY(-841);
    }

    private void initPanels() {
	initWoodenHammerPane();
	initMetalheadPane();
	initFierySmashPane();
	initFrostBitePane();
	initDuskPane();
	initTwilightPane();
	initStarryNightPane();
	initSpaceBlasterPane();
	initBubblesPane();
	initMjolnirPane();
    }

    private void initSpaceBlasterPane() {
	addPaneComponents(spaceBlasterPane, "SPACE BLASTER", Descriptions.SPACE_BLASTER);
	TextureRegion upstate, downstate, hoverstate;
	if (Settings.hasSpaceBlaster && Settings.hammerType != Hammer.SPACE_BLASTER)
	    addSpaceBlasterEquipButton();
	else if (!Settings.hasSpaceBlaster) {
	    upstate = Art.hammers.findRegion("BUY-UPSTATE");
	    downstate = Art.hammers.findRegion("BUY-DOWNSTATE");
	    hoverstate = Art.hammers.findRegion("BUY-UPSTATE");
	    DynamicButton buy = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

		@Override
		public void inputDown(float x, float y) {
		    super.inputDown(x - spaceBlasterPane.getX(), y - spaceBlasterPane.getY());
		}

		@Override
		public void inputUp(float x, float y) {
		    super.inputUp(x - spaceBlasterPane.getX(), y - spaceBlasterPane.getY());
		}
	    };
	    buy.setName("SPACE-BLASTER-BUY");
	    buy.setCallback(this);
	    buttons.add(buy);
	    spaceBlasterPane.add(buy);

	    DynamicText price = new DynamicText(Fonts.berlinSansx32OrangeStroke, "$ 35,000");
	    price.setRegistration(DynamicRegistration.CENTER_LEFT);
	    price.setPosition(181, 60);
	    price.setScale(.6f);
	    price.setName("PRICE");
	    spaceBlasterPane.add(price);
	}
	spaceBlasterPane.setX(129);
	spaceBlasterPane.setY(-841);
    }

    private void initStarryNightPane() {
	addPaneComponents(starryNightPane, "STARRY NIGHT", Descriptions.STARRY_NIGHT);
	TextureRegion upstate, downstate, hoverstate;
	if (Settings.hasStarryNight && Settings.hammerType != Hammer.STARRY_NIGHT)
	    addStarryNightEquipButton();
	else if (!Settings.hasStarryNight) {
	    upstate = Art.hammers.findRegion("BUY-UPSTATE");
	    downstate = Art.hammers.findRegion("BUY-DOWNSTATE");
	    hoverstate = Art.hammers.findRegion("BUY-UPSTATE");
	    DynamicButton buy = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

		@Override
		public void inputDown(float x, float y) {
		    super.inputDown(x - starryNightPane.getX(), y - starryNightPane.getY());
		}

		@Override
		public void inputUp(float x, float y) {
		    super.inputUp(x - starryNightPane.getX(), y - starryNightPane.getY());
		}
	    };
	    buy.setName("STARRY-NIGHT-BUY");
	    buy.setCallback(this);
	    buttons.add(buy);
	    starryNightPane.add(buy);

	    DynamicText price = new DynamicText(Fonts.berlinSansx32OrangeStroke, "$ 35,000");
	    price.setRegistration(DynamicRegistration.CENTER_LEFT);
	    price.setPosition(181, 60);
	    price.setScale(.6f);
	    price.setName("PRICE");
	    starryNightPane.add(price);
	}
	starryNightPane.setX(129);
	starryNightPane.setY(-636);
    }

    private void initTwilightPane() {
	addPaneComponents(twilightPane, "TWILIGHT", Descriptions.TWILIGHT);
	TextureRegion upstate, downstate, hoverstate;
	if (Settings.hasTwilight && Settings.hammerType != Hammer.TWILIGHT)
	    addTwilightEquipButton();
	else if (!Settings.hasTwilight) {
	    upstate = Art.hammers.findRegion("BUY-UPSTATE");
	    downstate = Art.hammers.findRegion("BUY-DOWNSTATE");
	    hoverstate = Art.hammers.findRegion("BUY-UPSTATE");
	    DynamicButton buy = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

		@Override
		public void inputDown(float x, float y) {
		    super.inputDown(x - twilightPane.getX(), y - twilightPane.getY());
		}

		@Override
		public void inputUp(float x, float y) {
		    super.inputUp(x - twilightPane.getX(), y - twilightPane.getY());
		}
	    };
	    buy.setName("TWILIGHT-BUY");
	    buy.setCallback(this);
	    buttons.add(buy);
	    twilightPane.add(buy);

	    DynamicText price = new DynamicText(Fonts.berlinSansx32OrangeStroke, "$ 25,000");
	    price.setRegistration(DynamicRegistration.CENTER_LEFT);
	    price.setPosition(181, 60);
	    price.setScale(.6f);
	    price.setName("PRICE");
	    twilightPane.add(price);
	}
	twilightPane.setX(129);
	twilightPane.setY(-431);
    }

    private void initWoodenHammerPane() {
	addPaneComponents(woodenHammerPane, "WOODEN HAMMER", Descriptions.WOODEN_HAMMER);
	if (Settings.hammerType != Hammer.WOODEN_HAMMER)
	    addWoodenHammerEquipButton();
	woodenHammerPane.setX(129);
	woodenHammerPane.setY(594);
    }

    private void removeButtonByName(String name) {
	for (int i = 0; i < buttons.size(); i++)
	    if (buttons.get(i).getName().equals(name))
		buttons.remove(i);
    }
}
