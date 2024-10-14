; ModuleID = '/tmp/out.ll'
source_filename = "stack/stack.c"
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

%struct.Element = type { i32, %struct.Element*, i32 }
%struct.pthread_attr_t = type { i32 }

@capacity = constant i32 20, align 4
@head = common global %struct.Element* null, align 8
@elemsBuf = common global [20 x %struct.Element] zeroinitializer, align 16
@free = common global [20 x i32] zeroinitializer, align 16
@.str = private unnamed_addr constant [14 x i8] c"Pushed %i-%i\0A\00", align 1
@.str.1 = private unnamed_addr constant [8 x i8] c"%i(%i) \00", align 1
@.str.2 = private unnamed_addr constant [2 x i8] c"\0A\00", align 1

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define void @init() #0 {
bb:
  %tmp = alloca i32, align 4
  store %struct.Element* null, %struct.Element** @head, align 8
  store i32 0, i32* %tmp, align 4
  br label %bb1

bb1:                                              ; preds = %bb21, %bb
  %tmp2 = load i32, i32* %tmp, align 4
  %tmp3 = icmp slt i32 %tmp2, 20
  br i1 %tmp3, label %bb4, label %bb24

bb4:                                              ; preds = %bb1
  %tmp5 = load i32, i32* %tmp, align 4
  %tmp6 = sext i32 %tmp5 to i64
  %tmp7 = getelementptr inbounds [20 x %struct.Element], [20 x %struct.Element]* @elemsBuf, i64 0, i64 %tmp6
  %tmp8 = getelementptr inbounds %struct.Element, %struct.Element* %tmp7, i32 0, i32 0
  store i32 100, i32* %tmp8, align 8
  %tmp9 = load i32, i32* %tmp, align 4
  %tmp10 = sext i32 %tmp9 to i64
  %tmp11 = getelementptr inbounds [20 x %struct.Element], [20 x %struct.Element]* @elemsBuf, i64 0, i64 %tmp10
  %tmp12 = getelementptr inbounds %struct.Element, %struct.Element* %tmp11, i32 0, i32 1
  store %struct.Element* null, %struct.Element** %tmp12, align 8
  %tmp13 = load i32, i32* %tmp, align 4
  %tmp14 = load i32, i32* %tmp, align 4
  %tmp15 = sext i32 %tmp14 to i64
  %tmp16 = getelementptr inbounds [20 x %struct.Element], [20 x %struct.Element]* @elemsBuf, i64 0, i64 %tmp15
  %tmp17 = getelementptr inbounds %struct.Element, %struct.Element* %tmp16, i32 0, i32 2
  store i32 %tmp13, i32* %tmp17, align 8
  %tmp18 = load i32, i32* %tmp, align 4
  %tmp19 = sext i32 %tmp18 to i64
  %tmp20 = getelementptr inbounds [20 x i32], [20 x i32]* @free, i64 0, i64 %tmp19
  store i32 1, i32* %tmp20, align 4
  br label %bb21

bb21:                                             ; preds = %bb4
  %tmp22 = load i32, i32* %tmp, align 4
  %tmp23 = add nsw i32 %tmp22, 1
  store i32 %tmp23, i32* %tmp, align 4
  br label %bb1

bb24:                                             ; preds = %bb1
  ret void
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define %struct.Element* @getFree() #0 {
bb:
  %tmp = alloca i32, align 4
  %tmp1 = alloca i32, align 4
  br label %bb2

bb2:                                              ; preds = %bb32, %bb
  store i32 -1, i32* %tmp, align 4
  call void (...) @gotFree() #2
  store i32 0, i32* %tmp1, align 4
  br label %bb3

bb3:                                              ; preds = %bb15, %bb2
  %tmp4 = load i32, i32* %tmp1, align 4
  %tmp5 = icmp slt i32 %tmp4, 20
  br i1 %tmp5, label %bb6, label %bb18

bb6:                                              ; preds = %bb3
  call void (...) @inLoop() #2
  %tmp7 = load i32, i32* %tmp1, align 4
  %tmp8 = sext i32 %tmp7 to i64
  %tmp9 = getelementptr inbounds [20 x i32], [20 x i32]* @free, i64 0, i64 %tmp8
  %tmp10 = load i32, i32* %tmp9, align 4
  %tmp11 = icmp ne i32 %tmp10, 0
  br i1 %tmp11, label %bb12, label %bb14

bb12:                                             ; preds = %bb6
  call void (...) @gotFree1() #2
  %tmp13 = load i32, i32* %tmp1, align 4
  store i32 %tmp13, i32* %tmp, align 4
  br label %bb18

bb14:                                             ; preds = %bb6
  br label %bb15

bb15:                                             ; preds = %bb14
  %tmp16 = load i32, i32* %tmp1, align 4
  %tmp17 = add nsw i32 %tmp16, 1
  store i32 %tmp17, i32* %tmp1, align 4
  br label %bb3

bb18:                                             ; preds = %bb12, %bb3
  call void (...) @gotFree2() #2
  %tmp19 = load i32, i32* %tmp, align 4
  %tmp20 = icmp sge i32 %tmp19, 0
  br i1 %tmp20, label %bb21, label %bb32

bb21:                                             ; preds = %bb18
  call void (...) @inIf() #2
  %tmp22 = load i32, i32* %tmp, align 4
  %tmp23 = sext i32 %tmp22 to i64
  %tmp24 = getelementptr inbounds [20 x i32], [20 x i32]* @free, i64 0, i64 %tmp23
  %tmp25 = cmpxchg i32* %tmp24, i32 1, i32 0 seq_cst seq_cst
  %tmp26 = extractvalue { i32, i1 } %tmp25, 1
  br i1 %tmp26, label %bb27, label %bb28

bb27:                                             ; preds = %bb21
  call void (...) @gotFree3() #2
  br label %bb28

bb28:                                             ; preds = %bb27, %bb21
  %tmp29 = load i32, i32* %tmp, align 4
  %tmp30 = sext i32 %tmp29 to i64
  %tmp31 = getelementptr inbounds [20 x %struct.Element], [20 x %struct.Element]* @elemsBuf, i64 0, i64 %tmp30
  ret %struct.Element* %tmp31

bb32:                                             ; preds = %bb18
  br label %bb2
}

declare void @gotFree(...) #1

declare void @inLoop(...) #1

declare void @gotFree1(...) #1

declare void @gotFree2(...) #1

declare void @inIf(...) #1

declare void @gotFree3(...) #1

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define void @push(i32 %arg) #0 {
bb:
  %tmp = alloca i32, align 4
  %tmp1 = alloca %struct.Element*, align 8
  %tmp2 = alloca %struct.Element*, align 8
  store i32 %arg, i32* %tmp, align 4
  %tmp3 = call %struct.Element* @getFree() #2
  store %struct.Element* %tmp3, %struct.Element** %tmp1, align 8
  call void (...) @pushed() #2
  %tmp4 = load i32, i32* %tmp, align 4
  %tmp5 = load %struct.Element*, %struct.Element** %tmp1, align 8
  %tmp6 = getelementptr inbounds %struct.Element, %struct.Element* %tmp5, i32 0, i32 0
  store i32 %tmp4, i32* %tmp6, align 8
  call void (...) @pushed2() #2
  br label %bb7

bb7:                                              ; preds = %bb12, %bb
  %tmp8 = load %struct.Element*, %struct.Element** @head, align 8
  store %struct.Element* %tmp8, %struct.Element** %tmp2, align 8
  call void (...) @pushed1() #2
  %tmp9 = load %struct.Element*, %struct.Element** %tmp2, align 8
  %tmp10 = load %struct.Element*, %struct.Element** %tmp1, align 8
  %tmp11 = getelementptr inbounds %struct.Element, %struct.Element* %tmp10, i32 0, i32 1
  store %struct.Element* %tmp9, %struct.Element** %tmp11, align 8
  br label %bb12

bb12:                                             ; preds = %bb7
  %tmp13 = load %struct.Element*, %struct.Element** %tmp2, align 8
  %tmp14 = ptrtoint %struct.Element* %tmp13 to i64
  %tmp15 = load %struct.Element*, %struct.Element** %tmp1, align 8
  %tmp16 = ptrtoint %struct.Element* %tmp15 to i64
  %tmp17 = cmpxchg i64* bitcast (%struct.Element** @head to i64*), i64 %tmp14, i64 %tmp16 seq_cst seq_cst
  %tmp18 = extractvalue { i64, i1 } %tmp17, 1
  %tmp19 = xor i1 %tmp18, true
  br i1 %tmp19, label %bb7, label %bb20

bb20:                                             ; preds = %bb12
  %tmp21 = call i32 (...) @pushed3() #2
  %tmp22 = load %struct.Element*, %struct.Element** %tmp1, align 8
  %tmp23 = getelementptr inbounds %struct.Element, %struct.Element* %tmp22, i32 0, i32 0
  %tmp24 = load i32, i32* %tmp23, align 8
  %tmp25 = load %struct.Element*, %struct.Element** %tmp1, align 8
  %tmp26 = getelementptr inbounds %struct.Element, %struct.Element* %tmp25, i32 0, i32 2
  %tmp27 = load i32, i32* %tmp26, align 8
  %tmp28 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([14 x i8], [14 x i8]* @.str, i32 0, i32 0), i32 %tmp24, i32 %tmp27) #2
  ret void
}

declare void @pushed(...) #1

declare void @pushed2(...) #1

declare void @pushed1(...) #1

declare i32 @pushed3(...) #1

declare i32 @printf(i8*, ...) #1

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @pop() #0 {
bb:
  %tmp = alloca i32, align 4
  %tmp1 = alloca %struct.Element*, align 8
  %tmp2 = alloca i32, align 4
  store %struct.Element* null, %struct.Element** %tmp1, align 8
  br label %bb3

bb3:                                              ; preds = %bb9, %bb
  %tmp4 = load %struct.Element*, %struct.Element** @head, align 8
  store %struct.Element* %tmp4, %struct.Element** %tmp1, align 8
  call void (...) @poppedHead() #2
  %tmp5 = load %struct.Element*, %struct.Element** %tmp1, align 8
  %tmp6 = icmp ne %struct.Element* %tmp5, null
  br i1 %tmp6, label %bb8, label %bb7

bb7:                                              ; preds = %bb3
  store i32 -1, i32* %tmp, align 4
  br label %bb31

bb8:                                              ; preds = %bb3
  br label %bb9

bb9:                                              ; preds = %bb8
  %tmp10 = load %struct.Element*, %struct.Element** %tmp1, align 8
  %tmp11 = ptrtoint %struct.Element* %tmp10 to i64
  %tmp12 = load %struct.Element*, %struct.Element** %tmp1, align 8
  %tmp13 = getelementptr inbounds %struct.Element, %struct.Element* %tmp12, i32 0, i32 1
  %tmp14 = load %struct.Element*, %struct.Element** %tmp13, align 8
  %tmp15 = ptrtoint %struct.Element* %tmp14 to i64
  %tmp16 = cmpxchg i64* bitcast (%struct.Element** @head to i64*), i64 %tmp11, i64 %tmp15 seq_cst seq_cst
  %tmp17 = extractvalue { i64, i1 } %tmp16, 1
  %tmp18 = xor i1 %tmp17, true
  br i1 %tmp18, label %bb3, label %bb19

bb19:                                             ; preds = %bb9
  call void (...) @popped() #2
  %tmp20 = load %struct.Element*, %struct.Element** %tmp1, align 8
  %tmp21 = getelementptr inbounds %struct.Element, %struct.Element* %tmp20, i32 0, i32 0
  %tmp22 = load i32, i32* %tmp21, align 8
  store i32 %tmp22, i32* %tmp2, align 4
  %tmp23 = load %struct.Element*, %struct.Element** %tmp1, align 8
  %tmp24 = getelementptr inbounds %struct.Element, %struct.Element* %tmp23, i32 0, i32 2
  %tmp25 = load i32, i32* %tmp24, align 8
  %tmp26 = sext i32 %tmp25 to i64
  %tmp27 = getelementptr inbounds [20 x i32], [20 x i32]* @free, i64 0, i64 %tmp26
  store i32 1, i32* %tmp27, align 4
  call void (...) @popped1() #2
  call void (...) @popped2() #2
  %tmp28 = load %struct.Element*, %struct.Element** %tmp1, align 8
  %tmp29 = getelementptr inbounds %struct.Element, %struct.Element* %tmp28, i32 0, i32 0
  store i32 100, i32* %tmp29, align 8
  %tmp30 = load i32, i32* %tmp2, align 4
  store i32 %tmp30, i32* %tmp, align 4
  br label %bb31

bb31:                                             ; preds = %bb19, %bb7
  %tmp32 = load i32, i32* %tmp, align 4
  ret i32 %tmp32
}

declare void @poppedHead(...) #1

declare void @popped(...) #1

declare void @popped1(...) #1

declare void @popped2(...) #1

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i8* @thread1(i8* %arg) #0 {
bb:
  %tmp = alloca i8*, align 8
  %tmp1 = alloca i8*, align 8
  store i8* %arg, i8** %tmp1, align 8
  call void @push(i32 20) #2
  %tmp2 = load i8*, i8** %tmp, align 8
  ret i8* %tmp2
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i8* @thread2(i8* %arg) #0 {
bb:
  %tmp = alloca i8*, align 8
  %tmp1 = alloca i8*, align 8
  store i8* %arg, i8** %tmp1, align 8
  %tmp2 = call i32 @pop() #2
  call void @push(i32 5) #2
  %tmp3 = load i8*, i8** %tmp, align 8
  ret i8* %tmp3
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @main() #0 {
bb:
  %tmp = alloca i32, align 4
  %tmp1 = alloca i64, align 8
  %tmp2 = alloca i64, align 8
  %tmp3 = alloca %struct.Element*, align 8
  call void @init() #2
  call void @push(i32 10) #2
  %tmp4 = call i32 @pthread_create(i64* %tmp1, %struct.pthread_attr_t* null, i8* (i8*)* @thread1, i8* null) #2
  %tmp5 = call i32 @pthread_create(i64* %tmp2, %struct.pthread_attr_t* null, i8* (i8*)* @thread2, i8* null) #2
  %tmp6 = load i64, i64* %tmp1, align 8
  %tmp7 = call i32 @pthread_join(i64 %tmp6, i8** null) #2
  %tmp8 = load i64, i64* %tmp2, align 8
  %tmp9 = call i32 @pthread_join(i64 %tmp8, i8** null) #2
  %tmp10 = load %struct.Element*, %struct.Element** @head, align 8
  store %struct.Element* %tmp10, %struct.Element** %tmp3, align 8
  br label %bb11

bb11:                                             ; preds = %bb14, %bb
  %tmp12 = load %struct.Element*, %struct.Element** %tmp3, align 8
  %tmp13 = icmp ne %struct.Element* %tmp12, null
  br i1 %tmp13, label %bb14, label %bb25

bb14:                                             ; preds = %bb11
  %tmp15 = load %struct.Element*, %struct.Element** %tmp3, align 8
  %tmp16 = getelementptr inbounds %struct.Element, %struct.Element* %tmp15, i32 0, i32 0
  %tmp17 = load i32, i32* %tmp16, align 8
  %tmp18 = load %struct.Element*, %struct.Element** %tmp3, align 8
  %tmp19 = getelementptr inbounds %struct.Element, %struct.Element* %tmp18, i32 0, i32 2
  %tmp20 = load i32, i32* %tmp19, align 8
  %tmp21 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([8 x i8], [8 x i8]* @.str.1, i32 0, i32 0), i32 %tmp17, i32 %tmp20) #2
  %tmp22 = load %struct.Element*, %struct.Element** %tmp3, align 8
  %tmp23 = getelementptr inbounds %struct.Element, %struct.Element* %tmp22, i32 0, i32 1
  %tmp24 = load %struct.Element*, %struct.Element** %tmp23, align 8
  store %struct.Element* %tmp24, %struct.Element** %tmp3, align 8
  br label %bb11

bb25:                                             ; preds = %bb11
  %tmp26 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @.str.2, i32 0, i32 0)) #2
  %tmp27 = load i32, i32* %tmp, align 4
  ret i32 %tmp27
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
