varying vec2 v_texCoords;
varying vec3 normal; //normal eye space

uniform vec3 L; //direction of light, normalized
uniform vec4 lightSpecular;
uniform vec4 lightAmbient;
uniform vec4 lightColor;
uniform vec4 globalAmbient;
uniform sampler2D u_texture;

void main()
{
    vec3 H = normalize(L + vec3(0,0,1));

    // Compute emissive term
    vec4 emissive = gl_FrontMaterial.emission;

    // Compute ambient term
    vec4 ambient = gl_FrontMaterial.ambient * (globalAmbient + lightAmbient);

    // Compute the diffuse term
    float diffuseLight = max(dot(normal,L), 0.0);
    vec4 diffuse = gl_FrontMaterial.diffuse * lightColor * diffuseLight;

    // Compute the specular term
    float specularLight = pow(max(0.0,dot(normal,H)), gl_FrontMaterial.shininess);
    if (diffuseLight <= 0.0)
        specularLight = 0.0;
    float specular = dot(gl_FrontMaterial.specular,lightSpecular) * specularLight;

    gl_FragColor = emissive +
        texture2D(u_texture, v_texCoords) * (ambient + diffuse + specular);
}
