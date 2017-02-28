package com.github.privacystreams.core.commons.print;

import com.github.privacystreams.core.Item;
import com.github.privacystreams.core.UQI;
import com.github.privacystreams.core.commons.ItemFunction;
import com.github.privacystreams.core.utils.Logging;

/**
 * Created by yuanchun on 27/12/2016.
 * Print the item for debugging
 */

final class ItemDebugPrinter extends ItemFunction<Void> {
    @Override
    public Void apply(UQI uqi, Item input) {
        Logging.debug(input.toString());
        return null;
    }

}