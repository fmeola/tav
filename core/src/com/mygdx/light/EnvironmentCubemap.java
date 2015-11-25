package com.mygdx.light;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.mygdx.camera.MyCamera;

/**
 * Fuente: http://stackoverflow.com/questions/21884805/libgdx-0-9-9-apply-cubemap-in-environment
 */
public class EnvironmentCubemap {

    private Pixmap[] data = new Pixmap[6];
    private ShaderProgram shader;
    private int u_worldTrans;
    private Mesh quad;
    private Matrix4 worldTrans;
    private Quaternion q;

    private static final String vsPath = "cubemap/cubemap-vs.glsl";
    private static final String fsPath = "cubemap/cubemap-fs.glsl";

    public EnvironmentCubemap (Pixmap cubemap) {
        int w = cubemap.getWidth();
        int h = cubemap.getHeight();
        for(int i=0; i<6; i++) {
            data[i] = new Pixmap(w / 4, h / 3, Pixmap.Format.RGB888);
        }
        for(int x=0; x<w; x++) {
            for (int y = 0; y < h; y++) {
                //-X
                if (x >= 0 && x <= w / 4 && y >= h / 3 && y <= h * 2 / 3)
                    data[1].drawPixel(x, y - h / 3, cubemap.getPixel(x, y));
                //+Y
                if (x >= w / 4 && x <= w / 2 && y >= 0 && y <= h / 3)
                    data[2].drawPixel(x - w / 4, y, cubemap.getPixel(x, y));
                //+Z
                if (x >= w / 4 && x <= w / 2 && y >= h / 3 && y <= h * 2 / 3)
                    data[4].drawPixel(x - w / 4, y - h / 3, cubemap.getPixel(x, y));
                //-Y
                if (x >= w / 4 && x <= w / 2 && y >= h * 2 / 3 && y <= h)
                    data[3].drawPixel(x - w / 4, y - h * 2 / 3, cubemap.getPixel(x, y));
                //+X
                if (x >= w / 2 && x <= w * 3 / 4 && y >= h / 3 && y <= h * 2 / 3)
                    data[0].drawPixel(x - w / 2, y - h / 3, cubemap.getPixel(x, y));
                //-Z
                if (x >= w * 3 / 4 && x <= w && y >= h / 3 && y <= h * 2 / 3)
                    data[5].drawPixel(x - w * 3 / 4, y - h / 3, cubemap.getPixel(x, y));
            }
        }
        cubemap.dispose();
        init();
    }

    private void init(){
        String vs = Gdx.files.internal(vsPath).readString();
        String fs = Gdx.files.internal(fsPath).readString();
        shader = new ShaderProgram(vs, fs);
        u_worldTrans = shader.getUniformLocation("u_worldTrans");
        quad = createQuad();
        worldTrans = new Matrix4();
        q = new Quaternion();
        initCubemap();
    }

    private void initCubemap(){
        //bind cubemap
        Gdx.gl20.glBindTexture(GL20.GL_TEXTURE_CUBE_MAP, 0);
        Gdx.gl20.glTexImage2D(GL20.GL_TEXTURE_CUBE_MAP_POSITIVE_X, 0, GL20.GL_RGB, data[0].getWidth(), data[0].getHeight(), 0, GL20.GL_RGB, GL20.GL_UNSIGNED_BYTE, data[0].getPixels());
        Gdx.gl20.glTexImage2D(GL20.GL_TEXTURE_CUBE_MAP_NEGATIVE_X, 0, GL20.GL_RGB, data[1].getWidth(), data[1].getHeight(), 0, GL20.GL_RGB, GL20.GL_UNSIGNED_BYTE, data[1].getPixels());
        Gdx.gl20.glTexImage2D(GL20.GL_TEXTURE_CUBE_MAP_POSITIVE_Y, 0, GL20.GL_RGB, data[2].getWidth(), data[2].getHeight(), 0, GL20.GL_RGB, GL20.GL_UNSIGNED_BYTE, data[2].getPixels());
        Gdx.gl20.glTexImage2D(GL20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, 0, GL20.GL_RGB, data[3].getWidth(), data[3].getHeight(), 0, GL20.GL_RGB, GL20.GL_UNSIGNED_BYTE, data[3].getPixels());
        Gdx.gl20.glTexImage2D(GL20.GL_TEXTURE_CUBE_MAP_POSITIVE_Z, 0, GL20.GL_RGB, data[4].getWidth(), data[4].getHeight(), 0, GL20.GL_RGB, GL20.GL_UNSIGNED_BYTE, data[4].getPixels());
        Gdx.gl20.glTexImage2D(GL20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, 0, GL20.GL_RGB, data[5].getWidth(), data[5].getHeight(), 0, GL20.GL_RGB, GL20.GL_UNSIGNED_BYTE, data[5].getPixels());
        Gdx.gl20.glTexParameteri ( GL20.GL_TEXTURE_CUBE_MAP, GL20.GL_TEXTURE_MIN_FILTER,GL20.GL_LINEAR_MIPMAP_LINEAR );
        Gdx.gl20.glTexParameteri ( GL20.GL_TEXTURE_CUBE_MAP, GL20.GL_TEXTURE_MAG_FILTER,GL20.GL_LINEAR );
        Gdx.gl20.glTexParameteri ( GL20.GL_TEXTURE_CUBE_MAP, GL20.GL_TEXTURE_WRAP_S, GL20.GL_CLAMP_TO_EDGE );
        Gdx.gl20.glTexParameteri ( GL20.GL_TEXTURE_CUBE_MAP, GL20.GL_TEXTURE_WRAP_T, GL20.GL_CLAMP_TO_EDGE );
        Gdx.gl20.glGenerateMipmap(GL20.GL_TEXTURE_CUBE_MAP);
    }

    public void render(MyCamera camera){
        camera.getVMatrix().getRotation(q, true);
        q.conjugate();
        worldTrans.idt();
        worldTrans.rotate(q);
        shader.begin();
        shader.setUniformMatrix(u_worldTrans, worldTrans.translate(0, 0, -1));
        quad.render(shader, GL20.GL_TRIANGLES);
        shader.end();
    }

    public Mesh createQuad(){
        Mesh mesh = new Mesh(true, 4, 6, VertexAttribute.Position(), VertexAttribute.  ColorUnpacked(), VertexAttribute.TexCoords(0));
        mesh.setVertices(new float[]
                {-1f, -1f, 0, 1, 1, 1, 1, 0, 1,
                        1f, -1f, 0, 1, 1, 1, 1, 1, 1,
                        1f, 1f, 0, 1, 1, 1, 1, 1, 0,
                        -1f, 1f, 0, 1, 1, 1, 1, 0, 0});
        mesh.setIndices(new short[] {0, 1, 2, 2, 3, 0});
        return mesh;
    }

}
