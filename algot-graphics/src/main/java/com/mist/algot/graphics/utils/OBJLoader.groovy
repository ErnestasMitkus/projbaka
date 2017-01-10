package com.mist.algot.graphics.utils

import com.mist.algot.graphics.models.RawModel
import com.mist.algot.graphics.rendering.Loader
import org.lwjgl.util.vector.Vector2f
import org.lwjgl.util.vector.Vector3f

class OBJLoader {

    static RawModel loadObjModel(String fileName, Loader loader) {
        Reader reader = new InputStreamReader(FileUtils.loadFile(fileName))

        String line;
        List<Vector3f> vertices = []
        List<Vector2f> textures = []
        List<Vector3f> normals = []
        List<Integer> indices = []

        try {

            while (true) {
                line = reader.readLine()
                String[] currentLine = line.replaceAll(/\s+/, " ").split(" ");
                if (line.startsWith("v ")) {
					Vector3f vertex = new Vector3f(
                            (float) Float.valueOf(currentLine[1]),
							(float) Float.valueOf(currentLine[2]),
							(float) Float.valueOf(currentLine[3]))
					vertices.add(vertex)
				} else if (line.startsWith("vt ")) {
					Vector2f texture = new Vector2f(
                            (float) Float.valueOf(currentLine[1]),
							(float) Float.valueOf(currentLine[2]))
					textures.add(texture);
				} else if (line.startsWith("vn ")) {
					Vector3f normal = new Vector3f(
                            (float) Float.valueOf(currentLine[1]),
							(float) Float.valueOf(currentLine[2]),
							(float) Float.valueOf(currentLine[3]))
					normals.add(normal);
				} else if (line.startsWith("f ")) {
					break;
				}
            }

            float[] textureArray = new float[vertices.size() * 2];
            float[] normalsArray = new float[vertices.size() * 3];

            while (line != null) {
                if (line.startsWith("f ")) {
                    String[] currentLine = line.replaceAll(/\s+/, " ").split(" ");
                    String[] vertex1 = currentLine[1].split("/");
                    String[] vertex2 = currentLine[2].split("/");
                    String[] vertex3 = currentLine[3].split("/");
                    processVertex(vertex1, indices, textures, normals, textureArray, normalsArray)
                    processVertex(vertex2, indices, textures, normals, textureArray, normalsArray)
                    processVertex(vertex3, indices, textures, normals, textureArray, normalsArray)
                }
                line = reader.readLine();
			}


		    float[] verticesArray = new float[vertices.size() * 3];
            int[] indicesArray = new int[indices.size()]

            int vertexPointer = 0
            for (Vector3f vertex : vertices) {
                verticesArray[vertexPointer++] = vertex.x
                verticesArray[vertexPointer++] = vertex.y
                verticesArray[vertexPointer++] = vertex.z
            }

            for (int i = 0; i < indices.size(); i++) {
                indicesArray[i] = indices[i]
            }

            return loader.loadToVAO(verticesArray, textureArray, normalsArray, indicesArray)
        } catch (Exception e) {
            throw new RuntimeException(e)
        } finally {
            reader.close()
        }
    }

    private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, float[] textureArray, float[] normalsArray) {
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);

        Vector2f currentTex = textures[Integer.parseInt(vertexData[1]) - 1]
        textureArray[currentVertexPointer * 2] = currentTex.x
        textureArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y

        Vector3f currentNorm = normals[Integer.parseInt(vertexData[2]) - 1]
        normalsArray[currentVertexPointer * 3] = currentNorm.x
        normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y
        normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z
    }
}
