package com.mist.algot.graphics.hidden.algorithms

import com.mist.algot.graphics.entities.Camera
import com.mist.algot.graphics.hidden.Exchange
import com.mist.algot.graphics.hidden.ExchangeProperties
import com.mist.algot.graphics.hidden.RemovalAlgorithm
import com.mist.algot.graphics.model.Face
import com.mist.algot.graphics.model.Indice

import static com.mist.algot.graphics.hidden.ExchangeProperties.CAMERA

class BackfaceCullingRemovalAlgorithm implements RemovalAlgorithm {

    @Override
    void apply(Exchange exchange) {
//        Objects.requireNonNull(exchange.has(CAMERA), "Exchange must have a camera property")
//
//        Camera camera = exchange.getProperty(CAMERA)
//        def cameraVector = camera.viewMatrix
//
//        exchange.entities.each {
//            def rawModel = it.model.rawModel
//            List<Face> faces = rawModel.mesh.faces
//            faces.each {
//                it.
//            }
//        }
    }
}


/*

int i;
Vector normal,   	// the polygon normal
	camera(0,0,0),   // the camera at 0,0,0
	cv;          	// the camera vector (or view vector)

// Loop through all the polygons
for(i = 0; i < faces; i++)
{
	// Transform our normal to view space...
	// This example assumes you've already precalculated the normal and
	// so all you must do is rotate it into world space by your matrix.
	// See my vector tutorial on how to calculate a polygon normal.
	normal = *face[i].normal;
	normal = normal * to_world_space;

	// Now calculate a vector from the camera to any point on the polygon
	cv.set(camera, world_space[ face[i].vertex[0] ] );

	// Now calculate the dot product of the normal and the camera vector
	if((cv.dot(&normal) > 0))
	{
    	// draw the polygon here, it's visible
	}
}

* */