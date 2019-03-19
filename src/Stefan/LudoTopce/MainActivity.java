package Stefan.LudoTopce;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import java.util.logging.Level;
import java.util.logging.Logger;



public class MainActivity extends Activity
{
    private int x,y,sw,sh,preckiVis=25, preckiSir=40, brPrecki,tx,ty,r=10, i,j;
    private Precka [] precki;
    private Bitmap pozadina, pozadinaSc, precka, preckaSc, topka, topkaSc, pocetna, pocetnaSc;
    private Bitmap creditsb, gameover, pause;
    private int brPrecka = 0, poeni=0, offset=0, vreme=3000;
    private boolean imaPodNego = true, premin=false, pocetenEkran=true,igraVoTek=false, pauza=false, 
            hiscore=false, credits=false, kraj=false;
    public static final String PREFS_NAME = "highscore";
    public SharedPreferences.Editor editor; 
    public SharedPreferences settings;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (!pocetenEkran && !credits && !hiscore)
            {
                pauza = !pauza;
            }
        }
        //return super.onKeyDown(keyCode, event);
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
            x = y = 0;
                            
                                
                                
             settings = getSharedPreferences(PREFS_NAME, 0);
             editor = settings.edit();
             
             
             if (settings.getInt("poeni1",-1)==-1)
             {
                 editor.putInt("poeni1", 0);
                 editor.putInt("poeni2", 0);
                 editor.putInt("poeni3", 0);
                 editor.putInt("poeni4", 0);
                 editor.putInt("poeni5", 0);
             }
             editor.commit();
            
      
           requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
             
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            sh = displaymetrics.heightPixels;
            sw = displaymetrics.widthPixels;
            
            brPrecki = (int)(sh/(preckiVis*2))-1;
            precki = new Precka[brPrecki];
            
            final SampleView pogled = new SampleView(this);
            
            for (i=0;i<brPrecki;i++)
            {
                   precki[i] = new Precka();
                   precki[i].brzina = (int)(Math.random()*5)+1;
                   precki[i].x = (int)(Math.random()*sw)-preckiSir/2;
                   precki[i].levo = Math.random() > 0.5;
                   precki[i].iskoristena = false;
            }
            brPrecka = 0;
            tx = precki[0].x+preckiSir/2;
            ty = 2*preckiVis-r;
            
            pozadina = BitmapFactory.decodeResource(getResources(), R.drawable.background);
            pozadinaSc = Bitmap.createScaledBitmap(pozadina, sw, sh, false);
            precka = BitmapFactory.decodeResource(getResources(), R.drawable.precki);
            preckaSc = Bitmap.createScaledBitmap(precka, preckiSir, preckiVis, false);
            topka = BitmapFactory.decodeResource(getResources(), R.drawable.topka);
            topkaSc = Bitmap.createScaledBitmap(topka, r*2, r*2, false);
            pocetna = BitmapFactory.decodeResource(getResources(), R.drawable.pocetna);
            pocetnaSc = Bitmap.createScaledBitmap(pocetna, sw, sh, false);
            creditsb= BitmapFactory.decodeResource(getResources(), R.drawable.credits);
            gameover =BitmapFactory.decodeResource(getResources(), R.drawable.gameover);
            pause=BitmapFactory.decodeResource(getResources(), R.drawable.pause);
            
            
            pogled.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {       
                
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    if (igraVoTek)
                    {
                        if (pauza)
                        {
                            if (credits)
                                credits = false;
                            else if (hiscore)
                                hiscore = false;
                            else if (pocetenEkran)
                            {
                                
                                if (event.getX()>=75 && event.getX()<=265 && event.getY()>=190 && event.getY()<=240)
                                {
                                    igraVoTek=true;
                                    pauza = false;
                                    pocetenEkran = false;
                                    kraj=false;
                                    poeni=0;
                                    vreme = 3000;
                                    for (i=0;i<brPrecki;i++)
                                    {
                                           precki[i] = new Precka();
                                           precki[i].brzina = (int)(Math.random()*5)+1;
                                           precki[i].x = (int)(Math.random()*sw)-preckiSir/2;
                                           precki[i].levo = Math.random() > 0.5;
                                           precki[i].iskoristena = false;
                                    }
                                    tx = precki[0].x+preckiSir/2;
                                    ty = 2*preckiVis-r;
                                    brPrecka = 0;
                                }
                                else if (event.getX()>=50 && event.getX()<=300 && event.getY()>=266 && event.getY()<=302)
                                {
                                    pocetenEkran=false;
                                    pauza=false;
                                }
                                else if (event.getX()>=73 && event.getX()<=266 && event.getY()>=322 && event.getY()<=373)
                                {
                                    hiscore = true;
                                }
                                else if (event.getX()>=92 && event.getX()<=230 && event.getY()>=412 && event.getY()<=435)
                                {
                                    credits = true;
                                }
                                else if (event.getX()>=70 && event.getX()<=270 && event.getY()>=462 && event.getY()<=485)
                                {
                                    System.exit(1);
                                }
                            }
                            else
                            {
                                if (event.getX()>=sw/2-pause.getWidth()/2 && event.getX()<=sw/2  && event.getY()>=sh/2 && event.getY()<=sh/2+pause.getHeight()/2)
                                {
                                    igraVoTek=true;
                                    pauza = false;
                                    pocetenEkran = false;
                                    kraj=false;
                                    poeni=0;
                                    vreme = 3000;
                                    for (i=0;i<brPrecki;i++)
                                    {
                                           precki[i] = new Precka();
                                           precki[i].brzina = (int)(Math.random()*5)+1;
                                           precki[i].x = (int)(Math.random()*sw)-preckiSir/2;
                                           precki[i].levo = Math.random() > 0.5;
                                           precki[i].iskoristena = false;
                                    }
                                    tx = precki[0].x+preckiSir/2;
                                    ty = 2*preckiVis-r;
                                     brPrecka = 0;
                                }
                                else if (event.getX()>=sw/2 && event.getX()<=sw/2+pause.getWidth()/2  && event.getY()>=sh/2 && event.getY()<=sh/2+pause.getHeight()/2) 
                                {
                                    pocetenEkran = true;
                                }
                            }
                        }
                        else if(kraj)
                        {
                            if (event.getX()>=sw/2-pause.getWidth()/2 && event.getX()<=sw/2  && event.getY()>=sh/2 && event.getY()<=sh/2+pause.getHeight()/2)
                                {
                                    igraVoTek=true;
                                    pauza = false;
                                    pocetenEkran = false;
                                    kraj=false;
                                    poeni=0;
                                    vreme = 3000;
                                    for (i=0;i<brPrecki;i++)
                                    {
                                           precki[i] = new Precka();
                                           precki[i].brzina = (int)(Math.random()*5)+1;
                                           precki[i].x = (int)(Math.random()*sw)-preckiSir/2;
                                           precki[i].levo = Math.random() > 0.5;
                                           precki[i].iskoristena = false;
                                    }
                                    tx = precki[0].x+preckiSir/2;
                                    ty = 2*preckiVis-r;
                                    brPrecka = 0;
                                }
                                else if (event.getX()>=sw/2 && event.getX()<=sw/2+pause.getWidth()/2  && event.getY()>=sh/2 && event.getY()<=sh/2+pause.getHeight()/2) 
                                {
                                    igraVoTek = false;
                                    pauza = false;
                                    kraj = false;
                                    pocetenEkran = true;
                                }
                        }
                        else
                        {
                            if (imaPodNego)
                            {
                                //if (event.getX() > sw/2)
                                //    tx = precki[brPrecka].x + preckiSir+6;
                                //else
                                //tx = precki[brPrecka].x - 7;
                                ty += 10;
                                imaPodNego = false;
                            }
                        }
                    }
                    else
                    {
                        if (event.getX()>=75 && event.getX()<=265 && event.getY()>=190 && event.getY()<=240)
                        {
                             igraVoTek=true;
                                    pauza = false;
                                    pocetenEkran = false;
                                    kraj=false;
                                    poeni=0;
                                    vreme = 3000;
                                    for (i=0;i<brPrecki;i++)
                                    {
                                           precki[i] = new Precka();
                                           precki[i].brzina = (int)(Math.random()*5)+1;
                                           precki[i].x = (int)(Math.random()*sw)-preckiSir/2;
                                           precki[i].levo = Math.random() > 0.5;
                                           precki[i].iskoristena = false;
                                    }
                                    tx = precki[0].x+preckiSir/2;
                                    ty = 2*preckiVis-r;
                                     brPrecka = 0;
                        }
                        else if (event.getX()>=73 && event.getX()<=266 && event.getY()>=322 && event.getY()<=373)
                        {
                            hiscore = true;
                        }
                        else if (event.getX()>=92 && event.getX()<=230 && event.getY()>=412 && event.getY()<=435)
                        {
                            credits = true;
                        }
                        else if (event.getX()>=70 && event.getX()<=270 && event.getY()>=462 && event.getY()<=485)
                        {
                            System.exit(1);
                        }
                        else if (hiscore)
                        {
                            hiscore = false;
                        }
                        else if (credits)
                        {
                            credits = false;
                        }
                        
                    }
                }
                else if ((event.getAction() == KeyEvent.KEYCODE_HOME) || 
                        (event.getAction() == KeyEvent.KEYCODE_BACK) ||
                        (event.getAction() == KeyEvent.KEYCODE_MENU))
                {
                    pauza = true;
                }
                                
                pogled.postInvalidate();
                return true;
            }
            });
   
            
            new Thread(new Runnable() {
                public void run() {
                    while(true)
                    {
                     if (igraVoTek && !pocetenEkran && !kraj && !pauza)
                     {
                        if (premin)
                        {
                            offset += 7;
                            ty -= 7;
                            if (ty <= 2*preckiVis-r)
                            {
                                ty = 2*preckiVis-r;
                                offset = 0;
                                premin = false;
                                for (j=0; j<brPrecki; j++)
                                            {
                                                precki[j].brzina = (int)(Math.random()*5)+1;
                                                //precki[j].x = (int)(Math.random()*sw)-preckiSir/2;
                                                precki[j].levo = Math.random() > 0.5;
                                                precki[j].iskoristena = false;
                                                brPrecka = 0;
                                            }
                                if (imaPodNego)
                                    tx = precki[0].x + preckiSir/2-r ;
                            }
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else
                        {
                            for (i=0;i<brPrecki;i++)
                            {
                                if (precki[i].levo)
                                        precki[i].x -= precki[i].brzina;
                                else
                                        precki[i].x += precki[i].brzina;

                                if (precki[i].x < 0)
                                    precki[i].levo = false;
                                else if (precki[i].x > sw-preckiSir)
                                    precki[i].levo = true;

                                if (!imaPodNego)
                                {
                                        if ( ((ty+r >= (((i+1)*2)*preckiVis)-1) && (ty+r <= (((i+1)*2)*preckiVis)+5))
                                        && (tx>=precki[i].x) && (tx<=precki[i].x+preckiSir-r))
                                        {
                                            imaPodNego = true; 
                                            brPrecka = i;
                                            if (! precki[i].iskoristena) 
                                            {
                                                    precki[i].iskoristena=true ; 
                                                       poeni++;
                                            }
                                        }
                                }
                            }

                            if (imaPodNego)
                                {
                                    if (precki[brPrecka].levo)
                                        tx -= precki[brPrecka].brzina;
                                    else
                                        tx += precki[brPrecka].brzina;
                                    if (brPrecka == brPrecki-1)
                                        premin=true;
                                }
                            else
                            {
                                ty+=4;
                                if (ty >= sh-2*preckiVis) 
                                    premin = true;
                            }
                            vreme--;
                            if (vreme==0)
                            {
                                kraj = true;
                                int [] niza = new int[5];
                                for (int i=0;i<5;i++)
                                    niza[i] = settings.getInt("poeni"+i, 0);
                                
                                for (int i=0;i<5;i++)
                                    if (poeni > niza[i])
                                    {
                                        for (int j=4;j>i;j--)
                                        {
                                            niza[j]=niza[j-1];
                                        }
                                        niza[i]=poeni;
                                        break;
                                    }
                                for (int i=0;i<5;i++)
                                    editor.putInt("poeni"+i, niza[i]);
                                editor.commit();
                            }
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                     else
                     {
                         try {
                                Thread.sleep(10);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                            }
                     }
                     pogled.postInvalidate();
                  }
                }
            }).start();

            
            setContentView(pogled);
    }

        
    private class SampleView extends View {
                
 
		public SampleView(Context context) {
			super(context);
			setFocusable(true);
		}
 
		@Override
		protected void onDraw(Canvas canvas) {
                    Paint p = new Paint();
			// smooths
			p.setAntiAlias(true);
			p.setColor(Color.RED);
			p.setStyle(Paint.Style.FILL_AND_STROKE ); 
			p.setStrokeWidth(1f);
			p.setTextSize(20);
                        
                    if (igraVoTek)
                    {
			canvas.drawColor(Color.BLACK);
			
                        
			canvas.drawBitmap(pozadinaSc, 0, 0, null);
                        canvas.drawText(String.valueOf(poeni), 10, 20, p);
                        canvas.drawText(String.valueOf(vreme/50), sw-30, 20, p);
			//p.setAlpha(0x80); 
                        for (int i=0;i<brPrecki;i++)
                        {
                        	canvas.drawBitmap(preckaSc, precki[i].x, ((i+1)*2)*preckiVis-offset, null);
                            //canvas.drawRect(precki[i].x, ((i+1)*2)*preckiVis, precki[i].x+preckiSir, ((i+1)*2+1)*preckiVis, p);
                        }
                        
                        for (int i=0;i<brPrecki;i++)
                        {
                        	canvas.drawBitmap(preckaSc, precki[i].x, sh+((i+1)*2)*preckiVis-offset, null);
                            //canvas.drawRect(precki[i].x, ((i+1)*2)*preckiVis, precki[i].x+preckiSir, ((i+1)*2+1)*preckiVis, p);
                        }
                        
                        canvas.drawBitmap(topkaSc, tx, ty-r, null);
                        //canvas.drawCircle(tx, ty, r, p);
                        
                        if (pauza)
                        {
                            canvas.drawBitmap(pause, sw/2-pause.getWidth()/2, sh/2-pause.getHeight()/2, null);
                            if (pocetenEkran)
                                canvas.drawBitmap(pocetnaSc, 0,0, null);
                            
                            if (credits)
                                canvas.drawBitmap(creditsb, 0,0, null);
                            else if (hiscore)
                            {
                                canvas.drawBitmap(pozadinaSc, 0, 0, null);
                                p.setColor(Color.BLACK);
                                p.setTextSize(50);
                                p.setStrokeWidth(2f);
                                p.setStyle(Paint.Style.FILL_AND_STROKE ); 
                                canvas.drawText("High scores", 30, 70, p);
                                
                                 for (int i=0;i<5;i++)
                                    canvas.drawText(""+(i+1)+ ". " + settings.getInt("poeni"+i, 0), 50, 80 + (i+1)*50, p);
                            }      
                        }
                        else if (kraj)
                            canvas.drawBitmap(gameover, sw/2-pause.getWidth()/2, sh/2-pause.getHeight()/2, null);
                    }
                    else if (credits)
                                canvas.drawBitmap(creditsb, 0,0, null);
                    else if (hiscore)
                            {
                                canvas.drawBitmap(pozadinaSc, 0, 0, null);
                                p.setColor(Color.BLACK);
                                p.setTextSize(50);
                                p.setStrokeWidth(2f);
                                p.setStyle(Paint.Style.FILL_AND_STROKE); 
                                canvas.drawText("High scores", 30, 70, p);
                                
                                 for (int i=0;i<5;i++)
                                
                                    canvas.drawText(""+(i+1)+ ". " + settings.getInt("poeni"+i, 0), 50, 90 + (i+1)*65, p);
                                 	p.setTextSize(50);
                                 	p.setStyle(Paint.Style.STROKE); 
                            }
                    else
                    {
                        canvas.drawBitmap(pocetnaSc, 0, 0, null);
                    }
		}
	}
}