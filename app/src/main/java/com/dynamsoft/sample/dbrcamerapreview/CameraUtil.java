package com.dynamsoft.sample.dbrcamerapreview;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Size;

public class CameraUtil {
    public static Rect boundaryRotate(Point orgPt, Rect rect , boolean bLeft ){
        float orgx = orgPt.x;
        float orgy = orgPt.y;

        float rotatex =orgy;
        float rotatey = orgx;
        float[]currentBoundary = new float [8];
        currentBoundary[0] = rect.left;
        currentBoundary[1] = rect.top;

        currentBoundary[2] = rect.right;
        currentBoundary[3] = rect.top;

        currentBoundary[4] = rect.right;
        currentBoundary[5] = rect.bottom;

        currentBoundary[6] = rect.left;
        currentBoundary[7] = rect.bottom;

        int[] rotateBoundary = new int [8];
        if(bLeft){
            rotateBoundary[6] = (int)((currentBoundary[0]-orgx)*Math.cos(Math.PI*0.5f)+(currentBoundary[1] - orgy)*Math.sin(Math.PI*0.5f)+rotatex);
            rotateBoundary[7] = (int)(-(currentBoundary[0]-orgx)*Math.sin(Math.PI*0.5f)+(currentBoundary[1] - orgy)*Math.cos(Math.PI*0.5f)+rotatey);

            rotateBoundary[0] = (int)((currentBoundary[2]-orgx)*Math.cos(Math.PI*0.5f)+(currentBoundary[3] - orgy)*Math.sin(Math.PI*0.5f)+rotatex);
            rotateBoundary[1] = (int)(-(currentBoundary[2]-orgx)*Math.sin(Math.PI*0.5f)+(currentBoundary[3] - orgy)*Math.cos(Math.PI*0.5f)+rotatey);

            rotateBoundary[2] = (int)((currentBoundary[4]-orgx)*Math.cos(Math.PI*0.5f)+(currentBoundary[5] - orgy)*Math.sin(Math.PI*0.5f)+rotatex);
            rotateBoundary[3] = (int)(-(currentBoundary[4]-orgx)*Math.sin(Math.PI*0.5f)+(currentBoundary[5] - orgy)*Math.cos(Math.PI*0.5f)+rotatey);

            rotateBoundary[4] = (int)((currentBoundary[6]-orgx)*Math.cos(Math.PI*0.5f)+(currentBoundary[7] - orgy)*Math.sin(Math.PI*0.5f)+rotatex);
            rotateBoundary[5] = (int)(-(currentBoundary[6]-orgx)*Math.sin(Math.PI*0.5f)+(currentBoundary[7] - orgy)*Math.cos(Math.PI*0.5f)+rotatey);
        }else
        {
            rotateBoundary[2] = (int)((currentBoundary[0]-orgx)*Math.cos(Math.PI*0.5f)-(currentBoundary[1] - orgy)*Math.sin(Math.PI*0.5f)+rotatex);
            rotateBoundary[3] = (int)(((currentBoundary[0]-orgx)*Math.sin(Math.PI*0.5f))+((currentBoundary[1] - orgy)*Math.cos(Math.PI*0.5f))+rotatey);

            rotateBoundary[4] = (int)((currentBoundary[2]-orgx)*Math.cos(Math.PI*0.5f)-(currentBoundary[3] - orgy)*Math.sin(Math.PI*0.5f)+rotatex);
            rotateBoundary[5] = (int)((currentBoundary[2]-orgx)*Math.sin(Math.PI*0.5f)+(currentBoundary[3] - orgy)*Math.cos(Math.PI*0.5f)+rotatey);

            rotateBoundary[6] = (int)((currentBoundary[4]-orgx)*Math.cos(Math.PI*0.5f)-(currentBoundary[5] - orgy)*Math.sin(Math.PI*0.5f)+rotatex);
            rotateBoundary[7] = (int)((currentBoundary[4]-orgx)*Math.sin(Math.PI*0.5f)+(currentBoundary[5] - orgy)*Math.cos(Math.PI*0.5f)+rotatey);

            rotateBoundary[0] = (int)((currentBoundary[6]-orgx)*Math.cos(Math.PI*0.5f)-(currentBoundary[7] - orgy)*Math.sin(Math.PI*0.5f)+rotatex);
            rotateBoundary[1] = (int)((currentBoundary[6]-orgx)*Math.sin(Math.PI*0.5f)+(currentBoundary[7] - orgy)*Math.cos(Math.PI*0.5f)+rotatey);
        }

        Rect rotateRect  = new Rect(rotateBoundary[0],rotateBoundary[1],rotateBoundary[2],rotateBoundary[5]);

        return rotateRect;
    }

    public static Rect boundaryRotate180(Point orgPt,Rect rect ){
        float orgx = orgPt.x;
        float orgy = orgPt.y;

        float rotatex =orgy;
        float rotatey = orgx;
        float[]currentBoundary = new float [8];
        currentBoundary[0] = rect.left;
        currentBoundary[1] = rect.top;

        currentBoundary[2] = rect.right;
        currentBoundary[3] = rect.top;

        currentBoundary[4] = rect.right;
        currentBoundary[5] = rect.bottom;

        currentBoundary[6] = rect.left;
        currentBoundary[7] = rect.bottom;
        int[] rotateBoundary = new int [8];
        rotateBoundary[4] =(int)(orgx - (currentBoundary[0]-orgx));
        rotateBoundary[5] =(int)(orgy - (currentBoundary[1]-orgy));

        rotateBoundary[6] =(int)(orgx - (currentBoundary[2]-orgx));
        rotateBoundary[7] =(int)(orgy - (currentBoundary[3]-orgy));

        rotateBoundary[0] =(int)(orgx - (currentBoundary[4]-orgx));
        rotateBoundary[1] =(int)(orgy - (currentBoundary[5]-orgy));

        rotateBoundary[2] =(int)(orgx - (currentBoundary[6]-orgx));
        rotateBoundary[3] =(int)(orgy - (currentBoundary[7]-orgy));
        Rect rotateRect  = new Rect(rotateBoundary[0],rotateBoundary[1],rotateBoundary[2],rotateBoundary[5]);

        return rotateRect;
    }

    private Rect ConvertViewRegionToVideoFrameRegion(Rect viewRegion, Rect frameSize,int nOrientationDisplayOffset,Size szCameraView){
        Rect convertRegion ;
        final int rotateDegree = nOrientationDisplayOffset;
        if(rotateDegree == 90){
            convertRegion =  boundaryRotate(new Point(szCameraView.getWidth()/2,szCameraView.getHeight()/2),viewRegion,true);
        }else if(rotateDegree == 180){
            convertRegion = boundaryRotate180(new Point(szCameraView.getWidth()/2,szCameraView.getHeight()/2),viewRegion);
        }else if(nOrientationDisplayOffset == 270){
            convertRegion =  boundaryRotate(new Point(szCameraView.getWidth()/2,szCameraView.getHeight()/2),viewRegion,false);
        }else{
            convertRegion = viewRegion;
        }

        int nViewW = szCameraView.getWidth();
        int nViewH = szCameraView.getHeight();
        float fScaleH = (nOrientationDisplayOffset%180 ==0)? 1.0f*frameSize.height()/nViewH:1.0f*frameSize.height()/nViewW;
        float fScaleW = (nOrientationDisplayOffset%180 ==0)?1.0f *frameSize.width()/nViewW:1.0f*frameSize.width()/nViewH;
        float fScale   = (fScaleH>fScaleW)?fScaleW:fScaleH;
        int boxLeft  =(int)(convertRegion.left*fScale);
        int boxTop   = (int)(convertRegion.top*fScale);
        int boxWidth = (int)(convertRegion.width()*fScale);
        int boxHeight = (int)(convertRegion.height()*fScale);
        Rect frameRegion = new Rect(boxLeft,boxTop,boxWidth+boxLeft,boxTop+boxHeight);
        return frameRegion;
    }

    private Rect ConvertFrameRegionToViewRegion(Rect frameRegion, Rect frameSize,int nOrientationDisplayOffset,Size szCameraView){

        Rect imageRect = frameSize;
        Rect roateRect =frameRegion;
        int rotateDegree = nOrientationDisplayOffset;

        if(rotateDegree == 90){
            roateRect =  boundaryRotate(new Point(imageRect.width()/2,imageRect.height()/2),frameRegion,false);
        }else if(rotateDegree == 180){
            roateRect = boundaryRotate180(new Point(imageRect.width()/2,imageRect.height()/2),frameRegion);
        }else if(nOrientationDisplayOffset == 270){
            roateRect =  boundaryRotate(new Point(imageRect.width()/2,imageRect.height()/2),frameRegion,true);
        }

        int nViewW = szCameraView.getWidth();
        int nViewH = szCameraView.getHeight();

        float fScaleH = (nOrientationDisplayOffset%180 ==0)? 1.0f*nViewH /imageRect.height():1.0f*nViewH /imageRect.width();
        float fScaleW = (nOrientationDisplayOffset%180 ==0)?1.0f *nViewW/imageRect.width():1.0f*nViewW /imageRect.height();
        float fScale   = (fScaleH>fScaleW)?fScaleW:fScaleH;

        int boxLeft  =(int)(roateRect.left*fScale);
        int boxTop   = (int)(roateRect.top*fScale);
        int boxWidth = (int)(roateRect.width()*fScale);
        int boxHeight = (int)(roateRect.height()*fScale);
        Rect viewRegion = new Rect(boxLeft,boxTop,boxWidth+boxLeft,boxTop+boxHeight);
        return viewRegion;

    }
}
