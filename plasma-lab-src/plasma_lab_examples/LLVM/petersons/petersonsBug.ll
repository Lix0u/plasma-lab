; ModuleID = '/tmp/out.ll'
source_filename = "petersons/petersonsBug.c"
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

%struct.Options = type { i32*, i32*, i32* }
%struct.pthread_attr_t = type { i32 }

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i8* @petersons1(i8* %arg) #0 {
bb:
  %tmp = alloca i8*, align 8
  %tmp1 = alloca %struct.Options*, align 8
  store i8* %arg, i8** %tmp, align 8
  %tmp2 = load i8*, i8** %tmp, align 8
  %tmp3 = bitcast i8* %tmp2 to %struct.Options*
  store %struct.Options* %tmp3, %struct.Options** %tmp1, align 8
  %tmp4 = load %struct.Options*, %struct.Options** %tmp1, align 8
  %tmp5 = getelementptr inbounds %struct.Options, %struct.Options* %tmp4, i32 0, i32 0
  %tmp6 = load i32*, i32** %tmp5, align 8
  store i32 0, i32* %tmp6, align 4
  %tmp7 = load %struct.Options*, %struct.Options** %tmp1, align 8
  %tmp8 = getelementptr inbounds %struct.Options, %struct.Options* %tmp7, i32 0, i32 2
  %tmp9 = load i32*, i32** %tmp8, align 8
  store i32 1, i32* %tmp9, align 4
  br label %bb10

bb10:                                             ; preds = %bb24, %bb
  %tmp11 = load %struct.Options*, %struct.Options** %tmp1, align 8
  %tmp12 = getelementptr inbounds %struct.Options, %struct.Options* %tmp11, i32 0, i32 1
  %tmp13 = load i32*, i32** %tmp12, align 8
  %tmp14 = load i32, i32* %tmp13, align 4
  %tmp15 = icmp ne i32 %tmp14, 0
  br i1 %tmp15, label %bb16, label %bb22

bb16:                                             ; preds = %bb10
  %tmp17 = load %struct.Options*, %struct.Options** %tmp1, align 8
  %tmp18 = getelementptr inbounds %struct.Options, %struct.Options* %tmp17, i32 0, i32 2
  %tmp19 = load i32*, i32** %tmp18, align 8
  %tmp20 = load i32, i32* %tmp19, align 4
  %tmp21 = icmp eq i32 %tmp20, 1
  br label %bb22

bb22:                                             ; preds = %bb16, %bb10
  %tmp23 = phi i1 [ false, %bb10 ], [ %tmp21, %bb16 ]
  br i1 %tmp23, label %bb24, label %bb25

bb24:                                             ; preds = %bb22
  br label %bb10

bb25:                                             ; preds = %bb22
  call void (...) @crit() #2
  %tmp26 = load %struct.Options*, %struct.Options** %tmp1, align 8
  %tmp27 = getelementptr inbounds %struct.Options, %struct.Options* %tmp26, i32 0, i32 0
  %tmp28 = load i32*, i32** %tmp27, align 8
  store i32 0, i32* %tmp28, align 4
  ret i8* null
}

declare void @crit(...) #1

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i8* @petersons2(i8* %arg) #0 {
bb:
  %tmp = alloca i8*, align 8
  %tmp1 = alloca %struct.Options*, align 8
  store i8* %arg, i8** %tmp, align 8
  %tmp2 = load i8*, i8** %tmp, align 8
  %tmp3 = bitcast i8* %tmp2 to %struct.Options*
  store %struct.Options* %tmp3, %struct.Options** %tmp1, align 8
  %tmp4 = load %struct.Options*, %struct.Options** %tmp1, align 8
  %tmp5 = getelementptr inbounds %struct.Options, %struct.Options* %tmp4, i32 0, i32 0
  %tmp6 = load i32*, i32** %tmp5, align 8
  store i32 1, i32* %tmp6, align 4
  %tmp7 = load %struct.Options*, %struct.Options** %tmp1, align 8
  %tmp8 = getelementptr inbounds %struct.Options, %struct.Options* %tmp7, i32 0, i32 2
  %tmp9 = load i32*, i32** %tmp8, align 8
  store i32 0, i32* %tmp9, align 4
  br label %bb10

bb10:                                             ; preds = %bb24, %bb
  %tmp11 = load %struct.Options*, %struct.Options** %tmp1, align 8
  %tmp12 = getelementptr inbounds %struct.Options, %struct.Options* %tmp11, i32 0, i32 1
  %tmp13 = load i32*, i32** %tmp12, align 8
  %tmp14 = load i32, i32* %tmp13, align 4
  %tmp15 = icmp ne i32 %tmp14, 0
  br i1 %tmp15, label %bb16, label %bb22

bb16:                                             ; preds = %bb10
  %tmp17 = load %struct.Options*, %struct.Options** %tmp1, align 8
  %tmp18 = getelementptr inbounds %struct.Options, %struct.Options* %tmp17, i32 0, i32 2
  %tmp19 = load i32*, i32** %tmp18, align 8
  %tmp20 = load i32, i32* %tmp19, align 4
  %tmp21 = icmp eq i32 %tmp20, 0
  br label %bb22

bb22:                                             ; preds = %bb16, %bb10
  %tmp23 = phi i1 [ false, %bb10 ], [ %tmp21, %bb16 ]
  br i1 %tmp23, label %bb24, label %bb25

bb24:                                             ; preds = %bb22
  br label %bb10

bb25:                                             ; preds = %bb22
  call void (...) @crit() #2
  %tmp26 = load %struct.Options*, %struct.Options** %tmp1, align 8
  %tmp27 = getelementptr inbounds %struct.Options, %struct.Options* %tmp26, i32 0, i32 0
  %tmp28 = load i32*, i32** %tmp27, align 8
  store i32 0, i32* %tmp28, align 4
  ret i8* null
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @main() #0 {
bb:
  %tmp = alloca [2 x i32], align 4
  %tmp1 = alloca i32, align 4
  %tmp2 = alloca %struct.Options, align 8
  %tmp3 = alloca %struct.Options, align 8
  %tmp4 = alloca i64, align 8
  %tmp5 = alloca i64, align 8
  %tmp6 = getelementptr inbounds [2 x i32], [2 x i32]* %tmp, i64 0, i64 0
  %tmp7 = getelementptr inbounds %struct.Options, %struct.Options* %tmp2, i32 0, i32 0
  store i32* %tmp6, i32** %tmp7, align 8
  %tmp8 = getelementptr inbounds [2 x i32], [2 x i32]* %tmp, i64 0, i64 1
  %tmp9 = getelementptr inbounds %struct.Options, %struct.Options* %tmp2, i32 0, i32 1
  store i32* %tmp8, i32** %tmp9, align 8
  %tmp10 = getelementptr inbounds %struct.Options, %struct.Options* %tmp2, i32 0, i32 2
  store i32* %tmp1, i32** %tmp10, align 8
  %tmp11 = getelementptr inbounds [2 x i32], [2 x i32]* %tmp, i64 0, i64 1
  %tmp12 = getelementptr inbounds %struct.Options, %struct.Options* %tmp3, i32 0, i32 0
  store i32* %tmp11, i32** %tmp12, align 8
  %tmp13 = getelementptr inbounds [2 x i32], [2 x i32]* %tmp, i64 0, i64 0
  %tmp14 = getelementptr inbounds %struct.Options, %struct.Options* %tmp3, i32 0, i32 1
  store i32* %tmp13, i32** %tmp14, align 8
  %tmp15 = getelementptr inbounds %struct.Options, %struct.Options* %tmp3, i32 0, i32 2
  store i32* %tmp1, i32** %tmp15, align 8
  %tmp16 = bitcast %struct.Options* %tmp2 to i8*
  %tmp17 = call i32 @pthread_create(i64* %tmp4, %struct.pthread_attr_t* null, i8* (i8*)* @petersons1, i8* %tmp16) #2
  %tmp18 = bitcast %struct.Options* %tmp3 to i8*
  %tmp19 = call i32 @pthread_create(i64* %tmp5, %struct.pthread_attr_t* null, i8* (i8*)* @petersons2, i8* %tmp18) #2
  %tmp20 = load i64, i64* %tmp4, align 8
  %tmp21 = call i32 @pthread_join(i64 %tmp20, i8** null) #2
  %tmp22 = load i64, i64* %tmp5, align 8
  %tmp23 = call i32 @pthread_join(i64 %tmp22, i8** null) #2
  ret i32 0
}

declare i32 @pthread_create(i64*, %struct.pthread_attr_t*, i8* (i8*)*, i8*) #1

declare i32 @pthread_join(i64, i8**) #1

attributes #0 = { noinline nounwind optnone sspstrong uwtable "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-jump-tables"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #2 = { nobuiltin }

!llvm.module.flags = !{!0, !1, !2}
!llvm.ident = !{!3}

!0 = !{i32 1, !"wchar_size", i32 4}
!1 = !{i32 7, !"PIC Level", i32 2}
!2 = !{i32 7, !"PIE Level", i32 2}
!3 = !{!"clang version 5.0.0 (tags/RELEASE_500/final)"}
