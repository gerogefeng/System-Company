package by.psu.logical.service.action;

import by.psu.logical.model.instrument.Instrument;
import by.psu.logical.model.instrument.InstrumentDeparture;
import by.psu.logical.service.AbstractService;

public class EquipmentDeparture extends AbstractService<Instrument> {
    public EquipmentDeparture() {
        super(Instrument.class);
    }
}
