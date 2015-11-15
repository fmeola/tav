varying vec4 position; //position of point, eye space


void main()
{
    //encode z in eye space
    vec4 enc = vec4(1.0, 255.0, 65025.0, 160581375.0) * position.z;
    enc = fract(enc);
    enc -= enc.yzww * vec4(1.0/255.0,1.0/255.0,1.0/255.0,0.0);
    
    gl_FragColor = enc;
    //gl_FragColor = vec4(1., position.z, position.z, 1.);
}

