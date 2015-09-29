varying vec2 v_texCoords;
varying vec3 normal; //normal eye space
varying vec4 position; //position of point, eye space


//light uniforms
uniform vec3 lightPosition; //position of light, eye space
uniform vec3 lightDirection; //direction of light, eye space
uniform vec3 cameraPosition; //position of camera, eye space
uniform float spotCutOff; //in degrees
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
    vec3 L = lightPosition - position.xyz;
    L = normalize(L);

    float angle = acos( dot(lightDirection,L) );
    angle = max(angle, 0.0);
    if (angle < radians(spotCutOff)) {
        //lighted
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
    
    else gl_FragColor = vec4(0.,0.,0.,1.);

}
