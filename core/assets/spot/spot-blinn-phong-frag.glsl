varying vec2 v_texCoords;
varying vec3 normal; //normal eye space
varying vec4 position; //position of point, eye space

//light uniforms
uniform vec4 lightPosition; //position of light, world space
uniform vec4 lightDirection; //direction of light, world space
uniform vec3 cameraPosition; //position of camera, world space
uniform float spotCutOff; //in degrees
uniform vec4 lightSpecular;
uniform vec4 lightAmbient;
uniform vec4 lightColor;
uniform vec4 globalAmbient;
uniform mat4 u_viewMatrix;

//material uniforms
uniform vec4 matSpecular, matAmbient, matDiffuse;
uniform float matShininess;
uniform sampler2D u_texture;

void main()
{
    vec4 lightPositionEye = u_viewMatrix*lightPosition;
    vec4 lightDirectionEye = u_viewMatrix*lightDirection;
    vec4 cameraPositionEye = u_viewMatrix*vec4(cameraPosition,1.);

    vec3 L = (lightPositionEye - position).xyz;
    L = normalize(L);
    float NdotL = dot(L,normal);
    if (NdotL > 0.0) {
        float dotProduct = dot(-L,lightDirectionEye.xyz);
        dotProduct = dotProduct / ( length(-lightDirectionEye.xyz) * length(-L) );
        float angle = acos( dotProduct );
        angle = max(angle, 0.0);
        if (angle < radians(spotCutOff)) {
            //lighted

            // Compute the diffuse term
            float diffuseLight = max(dot(normal,L), 0.0);
            vec4 diffuse = matDiffuse * lightColor * diffuseLight;

            vec3 V = normalize(cameraPositionEye.xyz - position.xyz);
            vec3 H = normalize(L + V);

            // Compute the specular term
            float specularHardness = pow(max(0.0,dot(H,normal)), matShininess);
            vec4 specular = matSpecular * lightSpecular * specularHardness;

            // Compute ambient term
            vec4 ambient = matAmbient * (globalAmbient + lightAmbient);

            gl_FragColor = texture2D(u_texture, v_texCoords) * (ambient + diffuse + specular);
        } else {
            gl_FragColor = vec4(0.,0.,0.,1.);
        }
    }
    else gl_FragColor = vec4(0.,0.,0.,1.);

}
