import io.cucumber.core.backend.ObjectFactory;
import io.cucumber.picocontainer.PicoFactory;

public class CucumberPicoInjector implements ObjectFactory {

    private final PicoFactory picoFactory = new PicoFactory();

    @Override
    public void start() {
        picoFactory.start();
    }

    @Override
    public void stop() {
        picoFactory.stop();
    }

    @Override
    public boolean addClass(Class<?> clazz) {
        return picoFactory.addClass(clazz);
    }

    @Override
    public <T> T getInstance(Class<T> type) {
        return picoFactory.getInstance(type);
    }
}