varying vec2 v_texCoords;
varying vec3 normal; //normal eye space
varying vec4 position; //position of point, eye space

//light uniforms
uniform vec3 direction; 
uniform vec3 cameraPosition; //position of camera, eye space
uniform vec4 lightSpecular;
uniform vec4 lightAmbient;
uniform vec4 lightColor;
uniform vec4 globalAmbient;

//material uniforms
uniform vec4 matSpecular, matAmbient, matDiffuse;
uniform float matShininess;
uniform sampler2D u_texture;

void main()
{
    vec3 L = normalize(direction);
    
    
    // Compute the diffuse term
    float diffuseLight = max(dot(normal,L), 0.0);
    vec4 diffuse = matDiffuse * lightColor * diffuseLight;

    vec3 V = normalize(cameraPosition - position.xyz);
    vec3 H = normalize(L + V);

    // Compute the specular term
    float specularHardness = pow(max(0.0,dot(H,normal)), matShininess);
    vec4 specular = matSpecular * lightSpecular * specularHardness;

    // Compute ambient term
    vec4 ambient = matAmbient * (globalAmbient + lightAmbient);
    

    gl_FragColor = texture2D(u_texture, v_texCoords) * (ambient + diffuse + specular);
}
