package br.net.eia.ui.images;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Images {
	public static final ImageView LOADING_JAVAFX;
	public static final ImageView LOADING;
	static {
		LOADING_JAVAFX = new ImageView(new Image(
				Images.class.getResourceAsStream("javafx-loading-100x100.gif")));
		LOADING = new ImageView(new Image(
				Images.class.getResourceAsStream("loading.gif")));
	}
}
