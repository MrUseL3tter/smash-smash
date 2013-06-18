package com.noobs2d.smashsmash.screen;

import java.util.ArrayList;

import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.noobs2d.smashsmash.Art;
import com.noobs2d.smashsmash.Fonts;
import com.noobs2d.smashsmash.Settings;
import com.noobs2d.smashsmash.hammer.Hammer;
import com.noobs2d.smashsmash.hammer.Hammer.Descriptions;
import com.noobs2d.tweenengine.utils.DynamicButton;
import com.noobs2d.tweenengine.utils.DynamicButton.DynamicButtonCallback;
import com.noobs2d.tweenengine.utils.DynamicDisplay.DynamicRegistration;
import com.noobs2d.tweenengine.utils.DynamicDisplayGroup;
import com.noobs2d.tweenengine.utils.DynamicScreen;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.noobs2d.tweenengine.utils.DynamicText;
import com.noobs2d.tweenengine.utils.DynamicValue;

public class HammerSelectionScreen extends DynamicScreen implements GestureListener, DynamicButtonCallback {

    private DynamicButton back;
    private DynamicText goldText;
    private DynamicValue goldValue = new DynamicValue(Settings.gold);
    private ArrayList<DynamicButton> buttons = new ArrayList<DynamicButton>();
    private DynamicDisplayGroup logo = new DynamicDisplayGroup();
    private DynamicDisplayGroup goldPane = new DynamicDisplayGroup();
    private DynamicDisplayGroup woodenHammerPane = new DynamicDisplayGroup();
    private DynamicDisplayGroup metalHeadPane = new DynamicDisplayGroup();
    private DynamicDisplayGroup fierySmashPane = new DynamicDisplayGroup();
    private DynamicDisplayGroup frostBitePane = new DynamicDisplayGroup();
    private DynamicDisplayGroup duskPane = new DynamicDisplayGroup();
    private DynamicDisplayGroup twilightPane = new DynamicDisplayGroup();
    private DynamicDisplayGroup starryNightPane = new DynamicDisplayGroup();
    private DynamicDisplayGroup spaceBlasterPane = new DynamicDisplayGroup();
    private DynamicDisplayGroup bubblesPane = new DynamicDisplayGroup();
    private DynamicDisplayGroup mjolnirPane = new DynamicDisplayGroup();

    private float paneMoveSpeed = 27f;
    private float firstItemY = 480;
    private float flingVelocityX, flingVelocityY;
    private boolean flinging = false;
    private boolean panning = false;

    public HammerSelectionScreen(Game game) {
	super(game, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
	initPanels();
	initGoldPane();

	TextureRegion upstate, hoverstate, downstate;
	upstate = Art.menu.findRegion("BACK-UPSTATE");
	downstate = Art.menu.findRegion("BACK-DOWNSTATE");
	hoverstate = Art.menu.findRegion("BACK-UPSTATE");
	back = new DynamicButton(upstate, hoverstate, downstate, 15, 15);
	back.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	back.setName("BACK");
	back.setCallback(this);
	buttons.add(back);

	DynamicSprite title = new DynamicSprite(Art.hammers.findRegion("STORE-LOGO"), Settings.SCREEN_WIDTH / 2, 750);
	title.setScale(.65f);
	DynamicSprite tag = new DynamicSprite(Art.hammers.findRegion("STORE-SUBLOGO"), Settings.SCREEN_WIDTH / 2, 699);
	tag.setScale(.65f);
	logo.add(title);
	logo.add(tag);
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
	flinging = true;
	flingVelocityX = velocityX * 0.5f;
	flingVelocityY = velocityY * 0.5f;
	return false;
    }

    @Override
    public boolean longPress(float x, float y) {
	return false;
    }

    @Override
    public void onButtonEvent(DynamicButton button, int eventType) {
	if (eventType == UP)
	    if (button.getName().equals("BACK"))
		game.setScreen(new MainMenuScreen(game));
	    else if (button.getName().equals("METALHEAD-BUY")) {
		if (Settings.buyHammer(Hammer.METAL_HEAD)) {
		    metalHeadPane.removeByName("METALHEAD-BUY");
		    metalHeadPane.removeByName("PRICE");
		    removeButtonByName("METALHEAD-BUY");
		    addMetalHeadEquipButton();
		    interpolateGoldValue(Settings.gold);
		} else
		    game.setScreen(new PromptScreen(game, null, this, PromptScreen.OK, "INSUFFICIENT GOLD!"));
	    } else if (button.getName().equals("FIERY-SMASH-BUY")) {
		if (Settings.buyHammer(Hammer.FIERY_SMASH)) {
		    fierySmashPane.removeByName("FIERY-SMASH-BUY");
		    fierySmashPane.removeByName("PRICE");
		    removeButtonByName("FIERY-SMASH-BUY");
		    addFierySmashEquipButton();
		    interpolateGoldValue(Settings.gold);
		} else
		    game.setScreen(new PromptScreen(game, null, this, PromptScreen.OK, "INSUFFICIENT GOLD!"));
	    } else if (button.getName().equals("FROST-BITE-BUY")) {
		if (Settings.buyHammer(Hammer.FROST_BITE)) {
		    frostBitePane.removeByName("FROST-BITE-BUY");
		    frostBitePane.removeByName("PRICE");
		    removeButtonByName("FROST-BITE-BUY");
		    addFrostBiteEquipButton();
		    interpolateGoldValue(Settings.gold);
		} else
		    game.setScreen(new PromptScreen(game, null, this, PromptScreen.OK, "INSUFFICIENT GOLD!"));
	    } else if (button.getName().equals("DUSK-BUY")) {
		if (Settings.buyHammer(Hammer.DUSK)) {
		    duskPane.removeByName("DUSK-BUY");
		    duskPane.removeByName("PRICE");
		    removeButtonByName("DUSK-BUY");
		    addDuskEquipButton();
		    interpolateGoldValue(Settings.gold);
		} else
		    game.setScreen(new PromptScreen(game, null, this, PromptScreen.OK, "INSUFFICIENT GOLD!"));
	    } else if (button.getName().equals("TWILIGHT-BUY")) {
		if (Settings.buyHammer(Hammer.TWILIGHT)) {
		    twilightPane.removeByName("TWILIGHT-BUY");
		    twilightPane.removeByName("PRICE");
		    removeButtonByName("TWILIGHT-BUY");
		    addTwilightEquipButton();
		    interpolateGoldValue(Settings.gold);
		} else
		    game.setScreen(new PromptScreen(game, null, this, PromptScreen.OK, "INSUFFICIENT GOLD!"));
	    } else if (button.getName().equals("STARRY-NIGHT-BUY")) {
		if (Settings.buyHammer(Hammer.STARRY_NIGHT)) {
		    starryNightPane.removeByName("STARRY-NIGHT-BUY");
		    starryNightPane.removeByName("PRICE");
		    removeButtonByName("STARRY-NIGHT-BUY");
		    addStarryNightEquipButton();
		    interpolateGoldValue(Settings.gold);
		} else
		    game.setScreen(new PromptScreen(game, null, this, PromptScreen.OK, "INSUFFICIENT GOLD!"));
	    } else if (button.getName().equals("SPACE-BLASTER-BUY")) {
		if (Settings.buyHammer(Hammer.SPACE_BLASTER)) {
		    spaceBlasterPane.removeByName("SPACE-BLASTER-BUY");
		    spaceBlasterPane.removeByName("PRICE");
		    removeButtonByName("SPACE-BLASTER-BUY");
		    addSpaceBlasterEquipButton();
		    interpolateGoldValue(Settings.gold);
		} else
		    game.setScreen(new PromptScreen(game, null, this, PromptScreen.OK, "INSUFFICIENT GOLD!"));
	    } else if (button.getName().equals("BUBBLES-BUY")) {
		if (Settings.buyHammer(Hammer.BUBBLES)) {
		    bubblesPane.removeByName("BUBBLES-BUY");
		    bubblesPane.removeByName("PRICE");
		    removeButtonByName("BUBBLES-BUY");
		    addBubblesEquipButton();
		    interpolateGoldValue(Settings.gold);
		} else
		    game.setScreen(new PromptScreen(game, null, this, PromptScreen.OK, "INSUFFICIENT GOLD!"));
	    } else if (button.getName().equals("MJOLNIR-BUY")) {
		if (Settings.buyHammer(Hammer.MJOLNIR)) {
		    bubblesPane.removeByName("MJOLNIR-BUY");
		    bubblesPane.removeByName("PRICE");
		    removeButtonByName("MJOLNIR-BUY");
		    addBubblesEquipButton();
		    interpolateGoldValue(Settings.gold);
		} else
		    game.setScreen(new PromptScreen(game, null, this, PromptScreen.OK, "INSUFFICIENT GOLD!"));
	    } else if (button.getName().equals("METALHEAD-EQUIP")) {
		unequipHammer();
		equipHammer(Hammer.METAL_HEAD);
	    } else if (button.getName().equals("FIERY-SMASH-EQUIP")) {
		unequipHammer();
		equipHammer(Hammer.FIERY_SMASH);
	    } else if (button.getName().equals("FROST-BITE-EQUIP")) {
		unequipHammer();
		equipHammer(Hammer.FROST_BITE);
	    } else if (button.getName().equals("DUSK-EQUIP")) {
		unequipHammer();
		equipHammer(Hammer.DUSK);
	    } else if (button.getName().equals("TWILIGHT-EQUIP")) {
		unequipHammer();
		equipHammer(Hammer.TWILIGHT);
	    } else if (button.getName().equals("STARRY-NIGHT-EQUIP")) {
		unequipHammer();
		equipHammer(Hammer.STARRY_NIGHT);
	    } else if (button.getName().equals("SPACE-BLASTER-EQUIP")) {
		unequipHammer();
		equipHammer(Hammer.SPACE_BLASTER);
	    } else if (button.getName().equals("BUBBLES-EQUIP")) {
		unequipHammer();
		equipHammer(Hammer.BUBBLES);
	    } else if (button.getName().equals("WOODEN-HAMMER-EQUIP")) {
		unequipHammer();
		equipHammer(Hammer.WOODEN_HAMMER);
	    } else if (button.getName().equals("MJOLNIR-EQUIP")) {
		unequipHammer();
		equipHammer(Hammer.MJOLNIR);
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
	mjolnirPane.setY(mjolnirPane.getY() - deltaY);

	return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
	return false;
    }

    @Override
    public void render(float delta) {
	super.render(delta);

	//	Gdx.gl10.glClearColor(0.25f, 1f, 0.25f, 1f);

	batch.begin();

	batch.draw(Art.hammers.findRegion("FILL-GRAY"), 61, -25, 1158, 850);

	duskPane.render(batch);
	fierySmashPane.render(batch);
	frostBitePane.render(batch);
	woodenHammerPane.render(batch);
	metalHeadPane.render(batch);
	twilightPane.render(batch);
	starryNightPane.render(batch);
	spaceBlasterPane.render(batch);
	bubblesPane.render(batch);
	mjolnirPane.render(batch);

	duskPane.update(delta);
	fierySmashPane.update(delta);
	frostBitePane.update(delta);
	metalHeadPane.update(delta);
	woodenHammerPane.update(delta);
	twilightPane.update(delta);
	starryNightPane.update(delta);
	spaceBlasterPane.update(delta);
	bubblesPane.update(delta);
	mjolnirPane.update(delta);

	// TOP MASK
	for (int i = 0; i < 47; i++)
	    for (int j = 0; j < 5; j++)
		batch.draw(Art.hammers.findRegion("FILL-GRAY"), 76 + i * 24, 776 - 24 * j, 26, 26);
	for (int i = 0; i < 47; i++)
	    for (int j = 0; j < 5; j++)
		batch.draw(Art.hammers.findRegion("FILL-GRAY"), 76 + i * 24, 776 - 24 * j, 26, 26);

	// BOTTOM MASK
	for (int i = 0; i < 47; i++)
	    for (int j = 0; j < 2; j++)
		batch.draw(Art.hammers.findRegion("FILL-GRAY"), 76 + i * 24, 0 + 24 * j, 26, 26);
	for (int i = 0; i < 47; i++)
	    for (int j = 0; j < 2; j++)
		batch.draw(Art.hammers.findRegion("FILL-GRAY"), 76 + i * 24, 0 + 24 * j, 26, 26);

	batch.draw(Art.hammers.findRegion("FILL-LEFT"), 61, -25, 26, 850);
	batch.draw(Art.hammers.findRegion("FILL-RIGHT"), 1193, -25, 26, 850);
	logo.render(batch);
	logo.update(delta);

	goldPane.render(batch);
	goldPane.update(delta);
	goldText.setText("" + (int) goldValue.getValue());
	goldValue.update(delta);

	back.render(batch);
	back.update(delta);

	batch.end();

	if (flinging) {
	    //	    System.out.println("FLINGING ~ " + test.getY());
	    flingVelocityX *= 0.98f;
	    flingVelocityY *= 0.98f;
	    //	    float increase = test.getY() - velY * delta;
	    //	    if (test.getY() + increase <= 800 && test.getY() - increase >= 00)
	    //	    test.setY(increase);
	    woodenHammerPane.setY(woodenHammerPane.getY() - flingVelocityY * delta);
	    metalHeadPane.setY(metalHeadPane.getY() - flingVelocityY * delta);
	    fierySmashPane.setY(fierySmashPane.getY() - flingVelocityY * delta);
	    frostBitePane.setY(frostBitePane.getY() - flingVelocityY * delta);
	    duskPane.setY(duskPane.getY() - flingVelocityY * delta);
	    twilightPane.setY(twilightPane.getY() - flingVelocityY * delta);
	    starryNightPane.setY(starryNightPane.getY() - flingVelocityY * delta);
	    spaceBlasterPane.setY(spaceBlasterPane.getY() - flingVelocityY * delta);
	    bubblesPane.setY(bubblesPane.getY() - flingVelocityY * delta);
	    mjolnirPane.setY(mjolnirPane.getY() - flingVelocityY * delta);
	    if (Math.abs(flingVelocityX) < 0.01f)
		flingVelocityX = 0;
	    if (Math.abs(flingVelocityY) < 0.01f)
		flingVelocityY = 0;
	}

	//	if (!panning && test.getY() >= 800) {
	//	    flinging = false;
	//	    test.setY(test.getY() - 10f);
	//	} else if (!panning && test.getY() < 0) {
	//	    flinging = false;
	//	    test.setY(test.getY() + 10f);
	//	}

	float roof = firstItemY + 200 * 7.75f;
	if (!panning && woodenHammerPane.getY() >= roof) {
	    flinging = false;
	    float y = woodenHammerPane.getY() - roof < paneMoveSpeed ? woodenHammerPane.getY() - roof : paneMoveSpeed;
	    woodenHammerPane.setY(woodenHammerPane.getY() - y);
	    metalHeadPane.setY(metalHeadPane.getY() - y);
	    fierySmashPane.setY(fierySmashPane.getY() - y);
	    frostBitePane.setY(frostBitePane.getY() - y);
	    duskPane.setY(duskPane.getY() - y);
	    twilightPane.setY(twilightPane.getY() - y);
	    starryNightPane.setY(starryNightPane.getY() - y);
	    spaceBlasterPane.setY(spaceBlasterPane.getY() - y);
	    bubblesPane.setY(bubblesPane.getY() - y);
	    mjolnirPane.setY(mjolnirPane.getY() - y);
	} else if (!panning && woodenHammerPane.getY() <= firstItemY) {
	    flinging = false;
	    float y = firstItemY - woodenHammerPane.getY() > paneMoveSpeed ? paneMoveSpeed : firstItemY - woodenHammerPane.getY();
	    woodenHammerPane.setY(woodenHammerPane.getY() + y);
	    metalHeadPane.setY(metalHeadPane.getY() + y);
	    fierySmashPane.setY(fierySmashPane.getY() + y);
	    frostBitePane.setY(frostBitePane.getY() + y);
	    duskPane.setY(duskPane.getY() + y);
	    twilightPane.setY(twilightPane.getY() + y);
	    starryNightPane.setY(starryNightPane.getY() + y);
	    spaceBlasterPane.setY(spaceBlasterPane.getY() + y);
	    bubblesPane.setY(bubblesPane.getY() + y);
	    mjolnirPane.setY(mjolnirPane.getY() + y);
	}
    }

    @Override
    public void show() {
	Gdx.input.setInputProcessor(new GestureDetector(20, 0.5f, 2, 1f, this) {

	    @Override
	    public boolean touchUp(float x, float y, int pointer, int button) {
		super.touchUp(x, y, pointer, button);
		x *= (float) Settings.SCREEN_WIDTH / Gdx.graphics.getWidth();
		y = (Gdx.graphics.getHeight() * camera.zoom - y) * Settings.SCREEN_HEIGHT / Gdx.graphics.getHeight();

		Rectangle topMaskBounds = new Rectangle(75, 670, 1129, 130);
		Rectangle bottomMaskBounds = new Rectangle(75, 0, 1129, 50);
		boolean hitsMasks = topMaskBounds.contains(x, y) || bottomMaskBounds.contains(x, y);

		if (!hitsMasks)
		    for (int i = 0; i < buttons.size(); i++)
			if (buttons.get(i).inputUp(x, y))
			    break;
		panning = false;
		return true;
	    }

	});
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
	panning = false;
	x *= (float) Settings.SCREEN_WIDTH / Gdx.graphics.getWidth();
	y = (Gdx.graphics.getHeight() * camera.zoom - y) * Settings.SCREEN_HEIGHT / Gdx.graphics.getHeight();
	return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
	x *= (float) Settings.SCREEN_WIDTH / Gdx.graphics.getWidth();
	y = (Gdx.graphics.getHeight() * camera.zoom - y) * Settings.SCREEN_HEIGHT / Gdx.graphics.getHeight();

	Rectangle topMaskBounds = new Rectangle(75, 670, 1129, 130);
	Rectangle bottomMaskBounds = new Rectangle(75, 0, 1129, 50);
	boolean hitsMasks = topMaskBounds.contains(x, y) || bottomMaskBounds.contains(x, y);

	if (!hitsMasks)
	    for (int i = 0; i < buttons.size(); i++)
		buttons.get(i).inputDown(x, y);
	flinging = false;
	return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
	return false;
    }

    private void addBubblesEquipButton() {
	if (bubblesPane.getByName("SOLD-OUT") == null) {
	    DynamicSprite soldOut = new DynamicSprite(Art.hammers.findRegion("SOLD-OUT"), 181, 35);
	    soldOut.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	    bubblesPane.add(soldOut);
	}

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	downstate = Art.hammers.findRegion("EQUIP-DOWNSTATE");
	hoverstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	DynamicButton equip = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

	    @Override
	    public boolean inputDown(float x, float y) {
		return super.inputDown(x - bubblesPane.getX(), y - bubblesPane.getY());
	    }

	    @Override
	    public boolean inputUp(float x, float y) {
		return super.inputUp(x - bubblesPane.getX(), y - bubblesPane.getY());
	    }

	};
	equip.setName("BUBBLES-EQUIP");
	equip.setCallback(this);
	buttons.add(equip);
	bubblesPane.add(equip);
    }

    private void addDuskEquipButton() {
	if (duskPane.getByName("SOLD-OUT") == null) {
	    DynamicSprite soldOut = new DynamicSprite(Art.hammers.findRegion("SOLD-OUT"), 181, 35);
	    soldOut.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	    duskPane.add(soldOut);
	}

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	downstate = Art.hammers.findRegion("EQUIP-DOWNSTATE");
	hoverstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	DynamicButton equip = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

	    @Override
	    public boolean inputDown(float x, float y) {
		return super.inputDown(x - duskPane.getX(), y - duskPane.getY());
	    }

	    @Override
	    public boolean inputUp(float x, float y) {
		return super.inputUp(x - duskPane.getX(), y - duskPane.getY());
	    }

	};
	equip.setName("DUSK-EQUIP");
	equip.setCallback(this);
	buttons.add(equip);
	duskPane.add(equip);
    }

    private void addFierySmashEquipButton() {
	if (fierySmashPane.getByName("SOLD-OUT") == null) {
	    DynamicSprite soldOut = new DynamicSprite(Art.hammers.findRegion("SOLD-OUT"), 181, 35);
	    soldOut.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	    fierySmashPane.add(soldOut);
	}

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	downstate = Art.hammers.findRegion("EQUIP-DOWNSTATE");
	hoverstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	DynamicButton equip = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

	    @Override
	    public boolean inputDown(float x, float y) {
		return super.inputDown(x - fierySmashPane.getX(), y - fierySmashPane.getY());
	    }

	    @Override
	    public boolean inputUp(float x, float y) {
		return super.inputUp(x - fierySmashPane.getX(), y - fierySmashPane.getY());
	    }

	};
	equip.setName("FIERY-SMASH-EQUIP");
	equip.setCallback(this);
	buttons.add(equip);
	fierySmashPane.add(equip);
    }

    private void addFrostBiteEquipButton() {
	if (frostBitePane.getByName("SOLD-OUT") == null) {
	    DynamicSprite soldOut = new DynamicSprite(Art.hammers.findRegion("SOLD-OUT"), 181, 35);
	    soldOut.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	    frostBitePane.add(soldOut);
	}

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	downstate = Art.hammers.findRegion("EQUIP-DOWNSTATE");
	hoverstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	DynamicButton equip = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

	    @Override
	    public boolean inputDown(float x, float y) {
		return super.inputDown(x - frostBitePane.getX(), y - frostBitePane.getY());
	    }

	    @Override
	    public boolean inputUp(float x, float y) {
		return super.inputUp(x - frostBitePane.getX(), y - frostBitePane.getY());
	    }

	};
	equip.setName("FROST-BITE-EQUIP");
	equip.setCallback(this);
	buttons.add(equip);
	frostBitePane.add(equip);
    }

    private void addMetalHeadEquipButton() {
	if (metalHeadPane.getByName("SOLD-OUT") == null) {
	    DynamicSprite soldOut = new DynamicSprite(Art.hammers.findRegion("SOLD-OUT"), 181, 35);
	    soldOut.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	    metalHeadPane.add(soldOut);
	}

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	downstate = Art.hammers.findRegion("EQUIP-DOWNSTATE");
	hoverstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	DynamicButton equip = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

	    @Override
	    public boolean inputDown(float x, float y) {
		return super.inputDown(x - metalHeadPane.getX(), y - metalHeadPane.getY());
	    }

	    @Override
	    public boolean inputUp(float x, float y) {
		return super.inputUp(x - metalHeadPane.getX(), y - metalHeadPane.getY());
	    }

	};
	equip.setName("METALHEAD-EQUIP");
	equip.setCallback(this);
	buttons.add(equip);
	metalHeadPane.add(equip);
    }

    private void addMjolnirEquipButton() {
	if (mjolnirPane.getByName("SOLD-OUT") == null) {
	    DynamicSprite soldOut = new DynamicSprite(Art.hammers.findRegion("SOLD-OUT"), 181, 35);
	    soldOut.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	    mjolnirPane.add(soldOut);
	}

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	downstate = Art.hammers.findRegion("EQUIP-DOWNSTATE");
	hoverstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	DynamicButton equip = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

	    @Override
	    public boolean inputDown(float x, float y) {
		return super.inputDown(x - mjolnirPane.getX(), y - mjolnirPane.getY());
	    }

	    @Override
	    public boolean inputUp(float x, float y) {
		return super.inputUp(x - mjolnirPane.getX(), y - mjolnirPane.getY());
	    }

	};
	equip.setName("MJOLNIR-EQUIP");
	equip.setCallback(this);
	buttons.add(equip);
	mjolnirPane.add(equip);
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
	if (spaceBlasterPane.getByName("SOLD-OUT") == null) {
	    DynamicSprite soldOut = new DynamicSprite(Art.hammers.findRegion("SOLD-OUT"), 181, 35);
	    soldOut.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	    spaceBlasterPane.add(soldOut);
	}

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	downstate = Art.hammers.findRegion("EQUIP-DOWNSTATE");
	hoverstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	DynamicButton equip = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

	    @Override
	    public boolean inputDown(float x, float y) {
		return super.inputDown(x - spaceBlasterPane.getX(), y - spaceBlasterPane.getY());
	    }

	    @Override
	    public boolean inputUp(float x, float y) {
		return super.inputUp(x - spaceBlasterPane.getX(), y - spaceBlasterPane.getY());
	    }

	};
	equip.setName("SPACE-BLASTER-EQUIP");
	equip.setCallback(this);
	buttons.add(equip);
	spaceBlasterPane.add(equip);
    }

    private void addStarryNightEquipButton() {
	if (starryNightPane.getByName("SOLD-OUT") == null) {
	    DynamicSprite soldOut = new DynamicSprite(Art.hammers.findRegion("SOLD-OUT"), 181, 35);
	    soldOut.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	    starryNightPane.add(soldOut);
	}

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	downstate = Art.hammers.findRegion("EQUIP-DOWNSTATE");
	hoverstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	DynamicButton equip = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

	    @Override
	    public boolean inputDown(float x, float y) {
		return super.inputDown(x - starryNightPane.getX(), y - starryNightPane.getY());
	    }

	    @Override
	    public boolean inputUp(float x, float y) {
		return super.inputUp(x - starryNightPane.getX(), y - starryNightPane.getY());
	    }

	};
	equip.setName("STARRY-NIGHT-EQUIP");
	equip.setCallback(this);
	buttons.add(equip);
	starryNightPane.add(equip);
    }

    private void addTwilightEquipButton() {
	if (twilightPane.getByName("SOLD-OUT") == null) {
	    DynamicSprite soldOut = new DynamicSprite(Art.hammers.findRegion("SOLD-OUT"), 181, 35);
	    soldOut.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	    twilightPane.add(soldOut);
	}

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	downstate = Art.hammers.findRegion("EQUIP-DOWNSTATE");
	hoverstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	DynamicButton equip = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

	    @Override
	    public boolean inputDown(float x, float y) {
		return super.inputDown(x - twilightPane.getX(), y - twilightPane.getY());
	    }

	    @Override
	    public boolean inputUp(float x, float y) {
		return super.inputUp(x - twilightPane.getX(), y - twilightPane.getY());
	    }

	};
	equip.setName("TWILIGHT-EQUIP");
	equip.setCallback(this);
	buttons.add(equip);
	twilightPane.add(equip);
    }

    private void addWoodenHammerEquipButton() {
	if (woodenHammerPane.getByName("SOLD-OUT") == null) {
	    DynamicSprite soldOut = new DynamicSprite(Art.hammers.findRegion("SOLD-OUT"), 181, 35);
	    soldOut.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	    woodenHammerPane.add(soldOut);
	}

	TextureRegion upstate, downstate, hoverstate;
	upstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	downstate = Art.hammers.findRegion("EQUIP-DOWNSTATE");
	hoverstate = Art.hammers.findRegion("EQUIP-UPSTATE");
	DynamicButton equip = new DynamicButton(upstate, hoverstate, downstate, new Vector2(842, 97)) {

	    @Override
	    public boolean inputDown(float x, float y) {
		return super.inputDown(x - woodenHammerPane.getX(), y - woodenHammerPane.getY());
	    }

	    @Override
	    public boolean inputUp(float x, float y) {
		return super.inputUp(x - woodenHammerPane.getX(), y - woodenHammerPane.getY());
	    }

	};
	equip.setName("WOODEN-HAMMER-EQUIP");
	equip.setCallback(this);
	buttons.add(equip);
	woodenHammerPane.add(equip);
    }

    private void equipHammer(int hammerType) {
	switch (hammerType) {
	    case Hammer.BUBBLES:
		Settings.hammerType = Hammer.BUBBLES;
		removeButtonByName("BUBBLES-EQUIP");
		bubblesPane.removeByName("BUBBLES-EQUIP");
		break;
	    case Hammer.DUSK:
		Settings.hammerType = Hammer.DUSK;
		removeButtonByName("DUSK-EQUIP");
		duskPane.removeByName("DUSK-EQUIP");
		break;
	    case Hammer.FIERY_SMASH:
		Settings.hammerType = Hammer.FIERY_SMASH;
		removeButtonByName("FIERY-SMASH-EQUIP");
		fierySmashPane.removeByName("FIERY-SMASH-EQUIP");
		break;
	    case Hammer.FROST_BITE:
		Settings.hammerType = Hammer.FROST_BITE;
		removeButtonByName("FROST-BITE-EQUIP");
		frostBitePane.removeByName("FROST-BITE-EQUIP");
		break;
	    case Hammer.METAL_HEAD:
		Settings.hammerType = Hammer.METAL_HEAD;
		removeButtonByName("METAL-HEAD-EQUIP");
		metalHeadPane.removeByName("METALHEAD-EQUIP");
		break;
	    case Hammer.MJOLNIR:
		Settings.hammerType = Hammer.MJOLNIR;
		removeButtonByName("MJOLNIR-EQUIP");
		mjolnirPane.removeByName("MJOLNIR-EQUIP");
		break;
	    case Hammer.SPACE_BLASTER:
		Settings.hammerType = Hammer.SPACE_BLASTER;
		removeButtonByName("SPACE-BLASTER-EQUIP");
		spaceBlasterPane.removeByName("SPACE-BLASTER-EQUIP");
		break;
	    case Hammer.STARRY_NIGHT:
		Settings.hammerType = Hammer.STARRY_NIGHT;
		removeButtonByName("STARRY-NIGHT-EQUIP");
		starryNightPane.removeByName("STARRY-NIGHT-EQUIP");
		break;
	    case Hammer.TWILIGHT:
		Settings.hammerType = Hammer.TWILIGHT;
		removeButtonByName("TWILIGHT-EQUIP");
		twilightPane.removeByName("TWILIGHT-EQUIP");
		break;
	    case Hammer.WOODEN_HAMMER:
		Settings.hammerType = Hammer.WOODEN_HAMMER;
		removeButtonByName("WOODEN-HAMMER-EQUIP");
		woodenHammerPane.removeByName("WOODEN-HAMMER-EQUIP");
		break;
	    default:
		assert false;
	}
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
		public boolean inputDown(float x, float y) {
		    return super.inputDown(x - bubblesPane.getX(), y - bubblesPane.getY());
		}

		@Override
		public boolean inputUp(float x, float y) {
		    return super.inputUp(x - bubblesPane.getX(), y - bubblesPane.getY());
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
	bubblesPane.setY(firstItemY - 200 * 8);
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
		public boolean inputDown(float x, float y) {
		    return super.inputDown(x - duskPane.getX(), y - duskPane.getY());
		}

		@Override
		public boolean inputUp(float x, float y) {
		    return super.inputUp(x - duskPane.getX(), y - duskPane.getY());
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
	duskPane.setY(firstItemY - 200 * 4);
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
		public boolean inputDown(float x, float y) {
		    return super.inputDown(x - fierySmashPane.getX(), y - fierySmashPane.getY());
		}

		@Override
		public boolean inputUp(float x, float y) {
		    return super.inputUp(x - fierySmashPane.getX(), y - fierySmashPane.getY());
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
	fierySmashPane.setY(firstItemY - 200 * 2);
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
		public boolean inputDown(float x, float y) {
		    return super.inputDown(x - frostBitePane.getX(), y - frostBitePane.getY());
		}

		@Override
		public boolean inputUp(float x, float y) {
		    return super.inputUp(x - frostBitePane.getX(), y - frostBitePane.getY());
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
	frostBitePane.setY(firstItemY - 200 * 3);
    }

    private void initGoldPane() {

	DynamicSprite pane = new DynamicSprite(Art.hammers.findRegion("GOLD-PANE"), 1265, 15);
	pane.setRegistration(DynamicRegistration.BOTTOM_RIGHT);
	goldPane.add(pane);

	goldText = new DynamicText(Fonts.berlinSansx32OrangeStroke, "" + Settings.gold);
	goldText.setPosition(1240, 65);
	goldText.setScale(.65f);
	goldText.setRegistration(DynamicRegistration.CENTER_RIGHT);
	goldPane.add(goldText);
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
		public boolean inputDown(float x, float y) {
		    return super.inputDown(x - metalHeadPane.getX(), y - metalHeadPane.getY());
		}

		@Override
		public boolean inputUp(float x, float y) {
		    return super.inputUp(x - metalHeadPane.getX(), y - metalHeadPane.getY());
		}
	    };
	    buy.setName("METALHEAD-BUY");
	    buy.setCallback(this);
	    buttons.add(buy);
	    metalHeadPane.add(buy);
	}
	metalHeadPane.setX(129);
	metalHeadPane.setY(firstItemY - 200 * 1);
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
		public boolean inputDown(float x, float y) {
		    return super.inputDown(x - mjolnirPane.getX(), y - mjolnirPane.getY());
		}

		@Override
		public boolean inputUp(float x, float y) {
		    return super.inputUp(x - mjolnirPane.getX(), y - mjolnirPane.getY());
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
	mjolnirPane.setY(firstItemY - 200 * 9);
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
		public boolean inputDown(float x, float y) {
		    return super.inputDown(x - spaceBlasterPane.getX(), y - spaceBlasterPane.getY());
		}

		@Override
		public boolean inputUp(float x, float y) {
		    return super.inputUp(x - spaceBlasterPane.getX(), y - spaceBlasterPane.getY());
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
	spaceBlasterPane.setY(firstItemY - 200 * 7);
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
		public boolean inputDown(float x, float y) {
		    return super.inputDown(x - starryNightPane.getX(), y - starryNightPane.getY());
		}

		@Override
		public boolean inputUp(float x, float y) {
		    return super.inputUp(x - starryNightPane.getX(), y - starryNightPane.getY());
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
	starryNightPane.setY(firstItemY - 200 * 6);
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
		public boolean inputDown(float x, float y) {
		    return super.inputDown(x - twilightPane.getX(), y - twilightPane.getY());
		}

		@Override
		public boolean inputUp(float x, float y) {
		    return super.inputUp(x - twilightPane.getX(), y - twilightPane.getY());
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
	twilightPane.setY(firstItemY - 200 * 5);
    }

    private void initWoodenHammerPane() {
	addPaneComponents(woodenHammerPane, "WOODEN HAMMER", Descriptions.WOODEN_HAMMER);
	DynamicSprite soldOut = new DynamicSprite(Art.hammers.findRegion("SOLD-OUT"), 181, 35);
	soldOut.setRegistration(DynamicRegistration.BOTTOM_LEFT);
	woodenHammerPane.add(soldOut);
	if (Settings.hammerType != Hammer.WOODEN_HAMMER)
	    addWoodenHammerEquipButton();
	woodenHammerPane.setX(129);
	woodenHammerPane.setY(firstItemY);
    }

    private void interpolateGoldValue(float target) {
	goldValue.getTweenManager().update(1000);
	goldValue.interpolate(target, Linear.INOUT, 500, true);
    }

    private void removeButtonByName(String name) {
	for (int i = 0; i < buttons.size(); i++)
	    if (buttons.get(i).getName().equals(name))
		buttons.remove(i);
    }

    private void unequipHammer() {
	switch (Settings.hammerType) {
	    case Hammer.BUBBLES:
		addBubblesEquipButton();
		break;
	    case Hammer.DUSK:
		addDuskEquipButton();
		break;
	    case Hammer.FIERY_SMASH:
		addFierySmashEquipButton();
		break;
	    case Hammer.FROST_BITE:
		addFrostBiteEquipButton();
		break;
	    case Hammer.METAL_HEAD:
		addMetalHeadEquipButton();
		break;
	    case Hammer.MJOLNIR:
		addMjolnirEquipButton();
		break;
	    case Hammer.SPACE_BLASTER:
		addSpaceBlasterEquipButton();
		break;
	    case Hammer.STARRY_NIGHT:
		addStarryNightEquipButton();
		break;
	    case Hammer.TWILIGHT:
		addTwilightEquipButton();
		break;
	    case Hammer.WOODEN_HAMMER:
		addWoodenHammerEquipButton();
		break;
	    default:
		assert false;
	}
    }
}
