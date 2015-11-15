uniform sampler2D shadowMap;
varying vec2 v_texCoord;

void main() {
    gl_FragColor = texture2D(shadowMap, v_texCoord);
}