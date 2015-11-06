varying vec4 v_color; 
varying vec2 v_texCoords;
uniform sampler2D u_texture;

void main() {
    gl_FragColor = texture2D(u_texture, v_texCoords);
//    gl_FragColor = vec4(v_texCoords.x,v_texCoords.y,0,1);
}