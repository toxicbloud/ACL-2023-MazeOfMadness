package com;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static org.mockito.Mockito.mock;

/**
 * GdxTestRunner class
 * use to launch unit test with libGDX in headless mode.
 *
 * @author Antonin Rousseau , aka toxicbloud on GitHub
 */
public class GdxTestRunner implements BeforeAllCallback, ApplicationListener {
    /**
     * The headless application.
     */
    private HeadlessApplication application;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        application = new HeadlessApplication(this, config);
        Gdx.gl = mock(GL20.class);
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    /**
     * Get the headless application.
     *
     * @return The headless application.
     */
    public Application getApplication() {
        return application;
    }

}
