package fr.pgah.libgdx;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Sprite {

  int longueurFenetre;
  int hauteurFenetre;
  Texture img;
  int coordX;
  int coordY;
  boolean versLaDroite;
  boolean versLeHaut;
  double facteurTaille;
  int vitesse;
  float rotation;
  int vitesseRotation;
  int longueurEffective;
  int hauteurEffective;
  Random generateurAleatoire;
  Rectangle hitboxSprite;

  public Sprite() {
    longueurFenetre = Gdx.graphics.getWidth();
    hauteurFenetre = Gdx.graphics.getHeight();
    generateurAleatoire = new Random();
    img = new Texture("sio.jpg");
    facteurTaille = 0.15;
    vitesse = 1 + generateurAleatoire.nextInt(4);
    rotation = 0;
    vitesseRotation = 5 + generateurAleatoire.nextInt(20);
    versLaDroite = generateurAleatoire.nextBoolean();
    versLeHaut = generateurAleatoire.nextBoolean();
    longueurEffective = (int) (img.getWidth() * facteurTaille);
    hauteurEffective = (int) (img.getHeight() * facteurTaille);
    coordX = generateurAleatoire.nextInt(longueurFenetre - longueurEffective);
    coordY = generateurAleatoire.nextInt(hauteurFenetre - hauteurEffective);
    hitboxSprite = new Rectangle(coordX, coordY, longueurEffective, hauteurEffective);
  }

  public void majEtat() {
    deplacer();
    pivoter();
    forcerAResterDansLeCadre();
  }

  public void pivoter() {
    rotation += vitesseRotation;
  }

  public void deplacer() {
    if (versLaDroite) {
      coordX += vitesse;
    } else {
      coordX -= vitesse;
    }
    if (versLeHaut) {
      coordY += vitesse;
    } else {
      coordY -= vitesse;
    }
    hitboxSprite.setPosition(coordX, coordY);
  }

  public void forcerAResterDansLeCadre() {
    // Gestion bordure droite
    if (coordX + longueurEffective > longueurFenetre) {
      coordX = longueurFenetre - longueurEffective;
      versLaDroite = false;
    }

    // Gestion bordure gauche
    if (coordX < 0) {
      coordX = 0;
      versLaDroite = true;
    }

    // Gestion bordures haute
    if (coordY + hauteurEffective > hauteurFenetre) {
      coordY = hauteurFenetre - hauteurEffective;
      versLeHaut = false;
    }

    // Gestion bordure basse
    if (coordY < 0) {
      coordY = 0;
      versLeHaut = true;
    }
  }

  public void dessiner(SpriteBatch batch) {
    batch.draw(img, coordX, coordY, longueurEffective / 2, hauteurEffective / 2, longueurEffective, hauteurEffective, 1,
        1, rotation, 0, 0, img.getWidth(), img.getHeight(), false, false);
  }

  public boolean seTrouveSur(int x, int y) {
    return hitboxSprite.contains(x, Gdx.graphics.getHeight() - y);
  }
}
