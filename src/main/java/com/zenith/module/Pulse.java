package com.zenith.module;

import com.collarmc.pounce.Subscribe;
import com.zenith.Proxy;
import com.zenith.event.proxy.PulseEmittedEvent;
import com.zenith.util.Queue;

import javax.swing.text.html.Option;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static com.zenith.util.Constants.MODULE_LOG;


public class Pulse extends Module{

    private Optional<Instant> lastPulse;
    public Pulse(Proxy proxy) {
        super(proxy);
        this.lastPulse = Optional.empty();
    }

    public Optional<Instant> getLastPulse() {
        return this.lastPulse;
    }
    private Duration getDurationSinceLastPulse() {
        if (this.lastPulse.isPresent()) {
            return Duration.between(this.lastPulse.get(), Instant.now());
        } else {
            return Duration.ZERO;
        }
    }
    @Subscribe
    public void handlePulseEmittedEvent(PulseEmittedEvent event) {
        // only respond to 1 pulse per 1000ms (1 per typical chunk load group)
        if (!this.lastPulse.isPresent() || Instant.now().toEpochMilli() - this.lastPulse.get().toEpochMilli() >= 1000) {
            MODULE_LOG.info("Pulse! time since last pulse: " + Queue.getEtaStringFromSeconds(getDurationSinceLastPulse().getSeconds()));
            this.lastPulse = Optional.of(Instant.now());
        }
    }
}
