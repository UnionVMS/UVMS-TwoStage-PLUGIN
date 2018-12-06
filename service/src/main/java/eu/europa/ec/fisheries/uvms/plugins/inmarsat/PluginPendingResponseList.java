package eu.europa.ec.fisheries.uvms.plugins.inmarsat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.LocalBean;
import java.util.ArrayList;
import java.util.List;

@LocalBean
public class PluginPendingResponseList {

    private static final Logger LOGGER = LoggerFactory.getLogger(PluginPendingResponseList.class);
    private ArrayList<InmarsatPendingResponse> pending = new ArrayList<>();



    public void addPendingPollResponse(InmarsatPendingResponse resp) {
        if (pending != null) {
            pending.add(resp);
            LOGGER.debug("Pending response added");
        }
    }

    public boolean removePendingPollResponse(InmarsatPendingResponse resp) {
        if (pending != null) {
            LOGGER.debug("Trying to remove pending poll response");
            return pending.remove(resp);
        }
        return false;
    }

    /* NOT USED REMOVE
    public List<InmarsatPendingResponse> getPendingPollResponses() {
        //noinspection unchecked
        return (ArrayList<InmarsatPendingResponse>) pending.clone();
    }

    public boolean containsPendingPollResponse(InmarsatPendingResponse resp) {
        return pending != null && pending.contains(resp);
    }
    */

    public InmarsatPendingResponse containsPollTo(String dnid, String memberId) {
        for (InmarsatPendingResponse element : pending) {
            if (element.getDnId().equalsIgnoreCase(dnid)
                    && element.getMembId().equalsIgnoreCase(memberId)) {
                return element;
            }
        }
        return null;
    }


}


