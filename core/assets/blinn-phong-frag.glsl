
precision highp float;

varying vec2 v_texCoords;
uniform vec3 normal;
uniform vec3 L; //direction of light, normalized
uniform vec4 matSpecular;
uniform vec4 matAmbient;
uniform vec4 matDiffuse;
uniform float matShininess;
uniform vec4 lightSpecular;
uniform vec4 lightAmbient;
uniform vec4 lightDiffuse;
uniform mat4 u_worldView;
uniform mat4 u_modelView;
uniform vec4 globalAmbient;

uniform sampler2D u_texture;

void main()
{
    vec3 H = normalize(L+vec3(0,0,1));
	
  float var = dot(matSpecular,lightSpecular)*pow(max(0.0,dot(normal,H)), matShininess);
	if (var < 0.0) var = 0.0;
	gl_FragColor = texture2D(u_texture, v_texCoords) * (matAmbient*(globalAmbient + lightAmbient) +
        matDiffuse*lightDiffuse*max(0.0,dot(normal,L)) + 
        var);
	
}
