package com.sunny.face.sharepreferences;

import de.devland.esperandro.SharedPreferenceActions;
import de.devland.esperandro.SharedPreferenceMode;
import de.devland.esperandro.annotations.SharedPreferences;

/**
 * Created by sunfusheng on 15/6/7.
 */
@SharedPreferences(name = "face_preferences", mode = SharedPreferenceMode.PRIVATE)
public interface FacePreferences extends SharedPreferenceActions {

    String faceId();
    void faceId(String faceId);

    String imgId();
    void imgId(String imgId);

    String personId();
    void personId(String personId);

}
