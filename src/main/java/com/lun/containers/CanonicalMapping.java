package com.lun.containers;

//: containers/CanonicalMapping.java
// Demonstrates WeakHashMap.
import java.util.*;

class Element {
	private String ident;

	public Element(String id) {
		ident = id;
	}

	public String toString() {
		return ident;
	}

	public int hashCode() {
		return ident.hashCode();
	}

	public boolean equals(Object r) {
		return r instanceof Element && ident.equals(((Element) r).ident);
	}

	protected void finalize() {
		System.out.println("Finalizing " + getClass().getSimpleName() + " " + ident);
	}
}

class Key extends Element {
	public Key(String id) {
		super(id);
	}
}

class Value extends Element {
	public Value(String id) {
		super(id);
	}
}

public class CanonicalMapping {
	public static void main(String[] args) {
		int size = 1000;
		// Or, choose size via the command line:
		if (args.length > 0)
			size = new Integer(args[0]);
		Key[] keys = new Key[size];
		WeakHashMap<Key, Value> map = new WeakHashMap<Key, Value>();
		for (int i = 0; i < size; i++) {
			Key k = new Key(Integer.toString(i));
			Value v = new Value(Integer.toString(i));
			if (i % 3 == 0)
				keys[i] = k; // Save as "real" references
			map.put(k, v);
		}
		System.gc();
	}
} /* 

you

Finalizing Key 496
Finalizing Key 493
Finalizing Key 491
Finalizing Key 490
Finalizing Key 488
Finalizing Key 487
Finalizing Key 485
Finalizing Key 484
Finalizing Key 482
Finalizing Key 481
Finalizing Key 479
Finalizing Key 478
Finalizing Key 476
Finalizing Key 475
Finalizing Key 473
Finalizing Key 472
Finalizing Key 470
Finalizing Key 469
Finalizing Key 467
Finalizing Key 466
Finalizing Key 464
Finalizing Key 463
Finalizing Key 461
Finalizing Key 460
Finalizing Key 458
Finalizing Key 457
Finalizing Key 455
Finalizing Key 454
Finalizing Key 452
Finalizing Key 451
Finalizing Key 449
Finalizing Key 448
Finalizing Key 446
Finalizing Key 445
Finalizing Key 443
Finalizing Key 442
Finalizing Key 440
Finalizing Key 439
Finalizing Key 437
Finalizing Key 436
Finalizing Key 434
Finalizing Key 433
Finalizing Key 431
Finalizing Key 430
Finalizing Key 428
Finalizing Key 427
Finalizing Key 425
Finalizing Key 424
Finalizing Key 422
Finalizing Key 421
Finalizing Key 419
Finalizing Key 418
Finalizing Key 416
Finalizing Key 415
Finalizing Key 413
Finalizing Key 412
Finalizing Key 410
Finalizing Key 409
Finalizing Key 407
Finalizing Key 406
Finalizing Key 404
Finalizing Key 403
Finalizing Key 401
Finalizing Key 400
Finalizing Key 398
Finalizing Key 397
Finalizing Key 395
Finalizing Key 394
Finalizing Key 392
Finalizing Key 391
Finalizing Key 389
Finalizing Key 388
Finalizing Key 386
Finalizing Key 385
Finalizing Key 383
Finalizing Key 382
Finalizing Key 380
Finalizing Key 379
Finalizing Key 377
Finalizing Key 376
Finalizing Key 374
Finalizing Key 373
Finalizing Key 371
Finalizing Key 370
Finalizing Key 368
Finalizing Key 367
Finalizing Key 365
Finalizing Key 364
Finalizing Key 362
Finalizing Key 361
Finalizing Key 359
Finalizing Key 358
Finalizing Key 356
Finalizing Key 355
Finalizing Key 353
Finalizing Key 352
Finalizing Key 350
Finalizing Key 349
Finalizing Key 347
Finalizing Key 346
Finalizing Key 344
Finalizing Key 343
Finalizing Key 341
Finalizing Key 340
Finalizing Key 338
Finalizing Key 337
Finalizing Key 335
Finalizing Key 334
Finalizing Key 332
Finalizing Key 331
Finalizing Key 329
Finalizing Key 328
Finalizing Key 326
Finalizing Key 325
Finalizing Key 323
Finalizing Key 322
Finalizing Key 320
Finalizing Key 319
Finalizing Key 317
Finalizing Key 316
Finalizing Key 314
Finalizing Key 313
Finalizing Key 311
Finalizing Key 310
Finalizing Key 308
Finalizing Key 307
Finalizing Key 305
Finalizing Key 304
Finalizing Key 302
Finalizing Key 301
Finalizing Key 299
Finalizing Key 298
Finalizing Key 296
Finalizing Key 295
Finalizing Key 293
Finalizing Key 292
Finalizing Key 290
Finalizing Key 289
Finalizing Key 287
Finalizing Key 286
Finalizing Key 284
Finalizing Key 283
Finalizing Key 281
Finalizing Key 280
Finalizing Key 278
Finalizing Key 277
Finalizing Key 275
Finalizing Key 274
Finalizing Key 272
Finalizing Key 271
Finalizing Key 269
Finalizing Key 268
Finalizing Key 266
Finalizing Key 265
Finalizing Key 263
Finalizing Key 262
Finalizing Key 260
Finalizing Key 259
Finalizing Key 257
Finalizing Key 256
Finalizing Key 254
Finalizing Key 253
Finalizing Key 251
Finalizing Key 250
Finalizing Key 248
Finalizing Key 247
Finalizing Key 245
Finalizing Key 244
Finalizing Key 242
Finalizing Key 241
Finalizing Key 239
Finalizing Key 238
Finalizing Key 236
Finalizing Key 235
Finalizing Key 233
Finalizing Key 232
Finalizing Key 230
Finalizing Key 229
Finalizing Key 227
Finalizing Key 226
Finalizing Key 224
Finalizing Key 223
Finalizing Key 221
Finalizing Key 220
Finalizing Key 218
Finalizing Key 217
Finalizing Key 215
Finalizing Key 214
Finalizing Key 212
Finalizing Key 211
Finalizing Key 209
Finalizing Key 208
Finalizing Key 206
 */// :~
