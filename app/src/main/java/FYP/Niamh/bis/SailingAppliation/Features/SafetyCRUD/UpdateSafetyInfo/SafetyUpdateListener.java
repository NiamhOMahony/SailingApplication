package FYP.Niamh.bis.SailingAppliation.Features.SafetyCRUD.UpdateSafetyInfo;

/*
 * Adapted from Michael Gleesons lecture on 12/11/2020 gleeson.io
 */

import FYP.Niamh.bis.SailingAppliation.Features.SafetyCRUD.CreateSafetyEquipment.Safety;

public interface SafetyUpdateListener {

    void onSafetyInfoUpdate(Safety safety, int position);
}
