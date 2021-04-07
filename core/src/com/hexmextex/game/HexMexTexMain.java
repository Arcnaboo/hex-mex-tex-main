package com.hexmextex.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.hexmextex.game.maps.GameHex;
import com.hexmextex.game.maps.GameMap;
import com.hexmextex.game.utils.*;

import java.util.ArrayList;
import java.util.List;

public class HexMexTexMain extends ApplicationAdapter {
	static final int SPRITE_COUNT = 12;
	OrthographicCamera camera;
	ExtendViewport extendViewport;
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	BitmapFont font;
	SpriteBatch ui;
	Stage stage;
	TextureAtlas textureAtlas;
	TextureAtlas textureAtlasGs;
	Sprite background;
	/*Texture textureSolid;
	Pixmap pix;*/
	long lastTouch;
	Game game;
	//GameMap gameMap;
	Score score;
	Array<Sprite> sprites;
	Array<Sprite> spritesGs;
	Skin skin;


	int WIDTH = 128;
	int HEIGHT = 72;
	float aspectRatio;
	enum status {
		MENU,
		GAME
	}

	@Override
	public void create () {
		stage = new Stage();
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		font = new BitmapFont();
		font.setColor(Color.YELLOW);
		score = Score.initScore();
		batch = new SpriteBatch();
		ui = new SpriteBatch();
		background = new Sprite(new Texture(Gdx.files.internal("hex_bg.png")));
		HEIGHT = Gdx.graphics.getHeight();
		WIDTH = Gdx.graphics.getWidth();
		aspectRatio = (float) WIDTH/ (float) HEIGHT;
		background.setSize(WIDTH, HEIGHT);
		camera = new OrthographicCamera(WIDTH, HEIGHT);
		camera.position.set((WIDTH / 2), HEIGHT / 2, 0);
		game = new Game();
		game.start();
		//gameMap = new GameMap(3, 7);
		/*pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pix.setColor(0x00FFBEFF);
		textureSolid = new Texture(pix);*/
		textureAtlas = new TextureAtlas("sprites.txt");
		textureAtlasGs = new TextureAtlas("sprites_gs.txt");
		sprites = textureAtlas.createSprites();
		spritesGs = textureAtlasGs.createSprites();
		for (Sprite sprite : sprites) {
			//sprite.setSize(Math.round(64 * 1.5),Math.round(48 * 1.7));
			sprite.setSize((WIDTH * 0.05f), WIDTH * 0.05f);
		}
		for (Sprite sprite : spritesGs) {
			//sprite.setSize(Math.round(64 * 1.5),Math.round(48 * 1.7));
			sprite.setSize((WIDTH * 0.08f), WIDTH * 0.08f);
		}
		shapeRenderer = new ShapeRenderer();
		lastTouch = System.currentTimeMillis();

		//extendViewport = new ExtendViewport(1280, 720, camera);
		//.setProjectionMatrix(camera.combined);
		//camera.translate(0,0);
	}

	@Override
	public void resize(int width, int height) {
		//extendViewport.update(width, height, true);
		//batch.setProjectionMatrix(camera.combined);
		System.out.println(String.format("%d %d", width, height));
	}

	public void drawPoly(Hex hex, float [] vertices, Color color, Matrix4 projectionMatrix) {
		shapeRenderer.setProjectionMatrix(projectionMatrix);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(color);
		float x1, x2, x3;
		float y1, y2, y3;
		x1 = (float) game.current.layout.hexToPixel(hex).x;
		y1 = (float) game.current.layout.hexToPixel(hex).y;
		y1 = toGraphicsY(y1);
		for (int i = 0; i < vertices.length; i+=2) {
			x2 = vertices[i];
			y2 = vertices[i + 1];
			y2 = toGraphicsY(y2);
			if (i == 10) {
				x3 = vertices[0];
				y3 = vertices[1];
			} else {
				x3 = vertices[i + 2];
				y3 = vertices[i + 3];

			}
			y3 = toGraphicsY(y3);
			shapeRenderer.triangle(x1, y1, x2, y2, x3, y3);
		}

		shapeRenderer.end();
	}

	public void drawLine(Vector2 start, Vector2 end, Color color, Matrix4 projectionMatrix)
	{
		Gdx.gl.glLineWidth(3);
		shapeRenderer.setProjectionMatrix(projectionMatrix);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.line(start, end);
		shapeRenderer.end();
		Gdx.gl.glLineWidth(1);
	}

	private float toGraphicsY(float y) {

		return (float)Gdx.graphics.getHeight() - y;
	}
	boolean pr = false;
	private void checkInputs() {
		long currentTime = System.currentTimeMillis();
		if ((currentTime - lastTouch) > 150 && (Gdx.input.isTouched() || Gdx.input.isButtonPressed(Input.Buttons.LEFT))) {
			lastTouch = currentTime;
			Vector2 vector2 = new Vector2(Gdx.input.getX(), Gdx.input.getY());

			Point point = new Point(vector2.x, vector2.y);
			System.out.println("mouse " + vector2 + " ");
			GameHex gameHex = game.current.getGameHexByPosition(game.current.layout.pixelToHex(point).hexRound());
			if (gameHex!=null) {
				//System.out.println(gameHex + " " + game.current.layout.hexToPixel(gameHex.position) + " " + toGraphicsY(game.current.layout.hexToPixel(gameHex.position).vector2().y));

				game.current.click(gameHex);


			}
			//else System.out.println("null")
		}
		if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
			pr = !pr;
		}
	}
	boolean oneTime = true;

	@Override
	public void render () {
		if (oneTime) {
			System.out.println("oen time");
			oneTime = false;
		}
		if (game.current.isOver()) {
				Dialog dialog = new Dialog("Information", skin);
				dialog.text("Game over!");
				dialog.show(stage);
				return;
		}
		if (score.getCurrentPlayerScore() >= game.getPointsRequiredForNextLevel()) {
			game.levelUp();
		}
		checkInputs();
		//Gdx.gl.glClear(0xffffffff);
		//Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClearColor(0.8f,0.8f,0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		score.addToScore(game.current.step());

		//System.out.println(score);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		background.draw(batch);
		for (GameHex gameHex : game.current.gameHexes) {
			Hex hex = gameHex.position;
			Sprite sprite = (!game.current.isGameHexClicked(gameHex)) ? sprites.get(gameHex.spriteIndex()) : spritesGs.get(gameHex.spriteIndex());
			Point point = game.current.layout.hexToPixel(hex);
			//point = new Point(point.x/2, point.y/2);
			if (pr)System.out.println(point.vector2());
			sprite.setCenter(point.vector2().x, toGraphicsY(point.vector2().y));
			//sprite.setCenter(point.vector2().x, point.vector2().y);
			//sprite.setOrigin((float)point.x, toGraphicsY((float)point.y));

			//sprite.setPosition((float)point.x, toGraphicsY((float)point.y));
//			sprite.setPosition((float)point.x, toGraphicsY((float)point.y));
			//System.out.println("" + point.x + " " + point.y);
			sprite.draw(batch);
		}
		//drawLine(new Vector2(0, 0), new Vector2(200, 200), Color.BLACK, batch.getProjectionMatrix());
		/*for (GameHex gameHex : gameMap.gameHexes) {
			Hex hex = gameHex.position;
			List<Vector2> vector2s = gameMap.layout.polygonCornersV(hex, 0);
			Vector2 c = gameMap.layout.hexToPixel(hex).vector2();
			Vector2 center = new Vector2(c.x, toGraphicsY(c.y));
			for (int i = 0; i < vector2s.size(); i++) {
				Vector2 a = vector2s.get(i);
				Vector2 aa = new Vector2(a.x, toGraphicsY(a.y));

				Vector2 b = (i == vector2s.size() - 1) ? vector2s.get(0) : vector2s.get(i + 1);
				Vector2 bb = new Vector2(b.x, toGraphicsY(b.y));
				//b.y = toGraphicsY(b.y);
				drawPoly(hex, gameMap.layout.polygonVertices(hex, 0), gameHex.getColour(), batch.getProjectionMatrix());
				drawLine(aa, bb, gameHex.getColour(), batch.getProjectionMatrix());
				drawLine(center,
						aa, Color.BLACK, batch.getProjectionMatrix());
			}
		}*/

		batch.end();
		ui.begin();

		font.draw(ui, "Current level: " + game.getCurrentLevel(), 50,Gdx.graphics.getHeight() - 50);
		font.draw(ui, "Points required for next level: " + game.getPointsRequiredForNextLevel(), 50,Gdx.graphics.getHeight() - 75);
		font.draw(ui, "Points: " + score.getCurrentPlayerScore(), 50,Gdx.graphics.getHeight() - 100);
		font.draw(ui, "Remaining moves: " + game.current.getMovesRemaining(), 50,Gdx.graphics.getHeight() - 125);
		font.draw(ui, String.format("Score: %d | Moves: %d", score.getCurrentPlayerScore(), game.current.getMovesRemaining()), 50, 50);
		ui.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
		textureAtlas.dispose();
	}
}
