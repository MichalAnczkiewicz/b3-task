import org.example.ui.CheckoutCartSteps;
// import org.example.ui.CheckoutCartStepss;
import org.example.ui.CucumberHooks;
import org.example.ui.TestContext;
import io.cucumber.core.backend.ObjectFactory;
import io.cucumber.picocontainer.PicoFactory;

public class CucumberPicoInjector implements ObjectFactory {

    private final PicoFactory picoFactory = new PicoFactory();


    public CucumberPicoInjector() {
        picoFactory.addClass(TestContext.class);
        picoFactory.addClass(CucumberHooks.class);
        picoFactory.addClass(CheckoutCartSteps.class);
        // picoFactory.addClass(CheckoutCartStepss.class);
    }

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