varying vec2 v_texCoords;
varying vec3 normal;
uniform vec3 L; //direction of light, normalized
uniform vec4 lightSpecular;
uniform vec4 lightAmbient;
uniform vec4 lightDiffuse;
uniform vec4 globalAmbient;

uniform sampler2D u_texture;

void main()
{
    vec3 H = normalize(L+vec3(0,0,1));
    float var = dot(gl_FrontMaterial.specular,lightSpecular)*pow(max(0.0,dot(normal,H)), gl_FrontMaterial.shininess);
    if (var < 0.0) var = 0.0;
    gl_FragColor = gl_FrontMaterial.emission + 
        texture2D(u_texture, v_texCoords) * (gl_FrontMaterial.ambient*(globalAmbient + lightAmbient) +
        gl_FrontMaterial.diffuse*lightDiffuse*max(0.0,dot(normal,L)) + 
        var);
}
