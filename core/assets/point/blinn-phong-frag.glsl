varying vec2 v_texCoords;
varying vec3 normal; //normal eye space
varying vec4 position; //position of point, eye space

uniform vec3 lightPosition; //position of light, eye space
uniform vec3 cameraPosition; //position of camera, eye space
uniform vec4 lightSpecular;
uniform vec4 lightAmbient;
uniform vec4 lightColor;
uniform vec4 globalAmbient;
uniform vec4 matSpecular, matAmbient, matDiffuse;
uniform float matShininess;
uniform sampler2D u_texture;

void main()
{
    vec3 L = lightPosition - position.xyz;
    L = normalize(L);
    
    // Compute the diffuse term
    float diffuseLight = max(dot(normal,L), 0.0);
    vec4 diffuse = matDiffuse * lightColor * diffuseLight;

    vec3 V = cameraPosition - position.xyz;
    vec3 H = normalize(L + V);

    // Compute the specular term
    float specularLight = pow(max(0.0,dot(normal,H)), matShininess);
    float specular = dot(matSpecular, lightSpecular) * specularLight;

    // Compute emissive term
    //vec4 emissive = gl_FrontMaterial.emission;

    // Compute ambient term
    vec4 ambient = matAmbient * (globalAmbient + lightAmbient);
    //vec4 ambient = vec4(0.,0.,0.,1.);
    

    gl_FragColor = //emissive +
        texture2D(u_texture, v_texCoords) * (ambient + diffuse + specular);

}
