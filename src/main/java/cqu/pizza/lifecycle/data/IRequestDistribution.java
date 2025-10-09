package cqu.pizza.lifecycle.data;

/**
 *
 * @author sisak
 */
/**
 * Supplies the next customer request according to some distribution.
 */
public interface IRequestDistribution {

    /**
     * Returns the next request.
     *
     * @return next {@link Request}
     */
    Request next();
}
