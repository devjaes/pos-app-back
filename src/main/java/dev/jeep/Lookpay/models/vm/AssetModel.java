package dev.jeep.Lookpay.models.vm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssetModel {
    private byte[] content;
    private String contentType;
}
