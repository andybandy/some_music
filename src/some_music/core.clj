(ns some-music.core
  (:use [overtone.live]))

; Kick
(def kick (sample (freesound-path 25649)))

(def one-twenty-bpm (metronome 120))

(defn looper [nome sound]
  (let [beat (nome)]
    (at (nome beat) (sound))
    (apply-at (nome (inc beat)) looper nome sound [])))

;(looper one-twenty-bpm kick)
(stop)

(def nome (metronome 120))
(nome)

(nome)

; Saw wave
(definst saw-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
  (* (env-gen (env-lin attack sustain release) 1 1 0 1 FREE)
     (saw freq)
     vol))

;(saw-wave 440)

(defn note->hz [music-note]
  (midi->hz (note music-note)))

; Some other Saw
(defn saw2 [music-note]
  (saw-wave (midi->hz (note music-note))))

;(saw2 :A4)
;(saw2 :C5)
;(saw2 :C4)

; Chord
(defn play-chord [a-chord]
  (doseq [note a-chord] (saw2 note)))

;(play-chord (chord :C4 :major))

(defn chord-progression-time []
  (let [time (now)]
    (at time (play-chord (chord :C4 :major)))
    (at (+ 2000 time) (play-chord (chord :G3 :major)))
    (at (+ 3000 time) (play-chord (chord :F3 :sus4)))
    (at (+ 4000 time) (play-chord (chord :F3 :major)))
    (at (+ 5000 time) (play-chord (chord :G3 :major)))))

;(chord-progression-time)

(defn chord-progression-beat [m beat-num]
  (at (m (+ 0 beat-num)) (play-chord (chord :C4 :major)))
  (at (m (+ 4 beat-num)) (play-chord (chord :G3 :major)))
  (at (m (+ 8 beat-num)) (play-chord (chord :A3 :minor)))
  (at (m (+ 12 beat-num)) (play-chord (chord :F3 :major))))

;(chord-progression-beat metro (metro))
