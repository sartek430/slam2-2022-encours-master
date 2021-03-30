package fr.pgah.libgdx;

import java.util.Random;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Intro extends ApplicationAdapter {

  int NB_SPRITES = 2;
  SpriteBatch batch;
  int longueurFenetre;
  int hauteurFenetre;
  ArrayList<Sprite> sprites = new ArrayList<Sprite>(NB_SPRITES);
  // Joueur joueur;
  boolean gameOver;
  Texture gameOverTexture;
  int difficulte = 2;
  Random random = new Random();

  @Override
  public void create() {
    batch = new SpriteBatch();
    longueurFenetre = Gdx.graphics.getWidth();
    hauteurFenetre = Gdx.graphics.getHeight();

    gameOver = false;
    gameOverTexture = new Texture("gameover.jpg");

    initialisationSprites();
    // initialiserJoueur();
  }

  private void initialisationSprites() {
    for (int i = 0; i < NB_SPRITES; i++) {
      sprites.add(new Sprite());
    }
  }

  /*
   * private void initialiserJoueur() { joueur = new Joueur(); }
   */

  @Override
  public void render() {
    if (!gameOver) {
      reinitialiserArrierePlan();
      majEtatProtagonistes();
      // majEtatJeu();
      majetatCurseur();
      if (getRandomBoolean(difficulte)) {
        sprites.add(new Sprite());
      }
    }
    dessiner();
  }

  static boolean getRandomBoolean(float probability) {
    double randomValue = Math.random() * 100;
    return randomValue <= probability;
  }

  private void estEnCollisionAvec(ArrayList<Sprite> sprites) {
    int tirX = Gdx.input.getX();
    int tirY = Gdx.input.getY();
    for (Sprite sprite : sprites) {
      if (sprite.seTrouveSur(tirX, tirY)) {
        sprites.remove(sprite);
        break;
      }
    }
    if (sprites.isEmpty()) {
      gameOver = true;
    }
  }

  private void majetatCurseur() {
    if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
      estEnCollisionAvec(sprites);
    }
  }

  private void reinitialiserArrierePlan() {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

  private void majEtatProtagonistes() { // Sprites
    for (Sprite sprite : sprites) {
      sprite.majEtat();
    }
  } // sprites[i].majEtat(); }
    // Joueur joueur.majEtat(); }

  /*
   * private void majEtatJeu() { (joueur.estEnCollisionAvec(sprites)) { gameOver =
   * true; } }
   */

  private void dessiner() {
    batch.begin();
    if (gameOver) {
      batch.draw(gameOverTexture, 0, 0, longueurFenetre, hauteurFenetre);
    } else {
      for (Sprite sprite : sprites) {
        sprite.dessiner(batch);
      }
      // joueur.dessiner(batch);
    }
    batch.end();
  }
}
