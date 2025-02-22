; ModuleID = '/tmp/out.ll'
source_filename = "gossip/gossip.c"
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

%struct.setup = type { i32*, i32*, i32 }
%struct.pthread_attr_t = type { i32 }

@.str = private unnamed_addr constant [3 x i8] c"%i\00", align 1

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @VERIFIERError() #0 {
bb:
  %tmp = alloca i32, align 4
  %tmp1 = load i32, i32* %tmp, align 4
  ret i32 %tmp1
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define void @VERIFIERFinished() #0 {
bb:
  ret void
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i8* @protocol(i8* %arg) #0 {
bb:
  %tmp = alloca i8*, align 8
  %tmp1 = alloca %struct.setup*, align 8
  %tmp2 = alloca i32, align 4
  store i8* %arg, i8** %tmp, align 8
  %tmp3 = load i8*, i8** %tmp, align 8
  %tmp4 = bitcast i8* %tmp3 to %struct.setup*
  store %struct.setup* %tmp4, %struct.setup** %tmp1, align 8
  br label %bb5

bb5:                                              ; preds = %bb57, %bb
  %tmp6 = load %struct.setup*, %struct.setup** %tmp1, align 8
  %tmp7 = getelementptr inbounds %struct.setup, %struct.setup* %tmp6, i32 0, i32 0
  %tmp8 = load i32*, i32** %tmp7, align 8
  %tmp9 = load i32, i32* %tmp8, align 4
  %tmp10 = icmp ne i32 %tmp9, 3
  br i1 %tmp10, label %bb11, label %bb58

bb11:                                             ; preds = %bb5
  %tmp12 = call i32 @rand() #2
  %tmp13 = srem i32 %tmp12, 2
  store i32 %tmp13, i32* %tmp2, align 4
  %tmp14 = load %struct.setup*, %struct.setup** %tmp1, align 8
  %tmp15 = getelementptr inbounds %struct.setup, %struct.setup* %tmp14, i32 0, i32 0
  %tmp16 = load i32*, i32** %tmp15, align 8
  %tmp17 = load i32, i32* %tmp16, align 4
  %tmp18 = load %struct.setup*, %struct.setup** %tmp1, align 8
  %tmp19 = getelementptr inbounds %struct.setup, %struct.setup* %tmp18, i32 0, i32 1
  %tmp20 = load i32*, i32** %tmp19, align 8
  %tmp21 = load i32, i32* %tmp2, align 4
  %tmp22 = sext i32 %tmp21 to i64
  %tmp23 = getelementptr inbounds i32, i32* %tmp20, i64 %tmp22
  store i32 %tmp17, i32* %tmp23, align 4
  %tmp24 = load %struct.setup*, %struct.setup** %tmp1, align 8
  %tmp25 = getelementptr inbounds %struct.setup, %struct.setup* %tmp24, i32 0, i32 1
  %tmp26 = load i32*, i32** %tmp25, align 8
  %tmp27 = load %struct.setup*, %struct.setup** %tmp1, align 8
  %tmp28 = getelementptr inbounds %struct.setup, %struct.setup* %tmp27, i32 0, i32 2
  %tmp29 = load i32, i32* %tmp28, align 8
  %tmp30 = sext i32 %tmp29 to i64
  %tmp31 = getelementptr inbounds i32, i32* %tmp26, i64 %tmp30
  %tmp32 = load i32, i32* %tmp31, align 4
  %tmp33 = icmp ne i32 %tmp32, 0
  br i1 %tmp33, label %bb34, label %bb57

bb34:                                             ; preds = %bb11
  %tmp35 = load %struct.setup*, %struct.setup** %tmp1, align 8
  %tmp36 = getelementptr inbounds %struct.setup, %struct.setup* %tmp35, i32 0, i32 1
  %tmp37 = load i32*, i32** %tmp36, align 8
  %tmp38 = load %struct.setup*, %struct.setup** %tmp1, align 8
  %tmp39 = getelementptr inbounds %struct.setup, %struct.setup* %tmp38, i32 0, i32 2
  %tmp40 = load i32, i32* %tmp39, align 8
  %tmp41 = sext i32 %tmp40 to i64
  %tmp42 = getelementptr inbounds i32, i32* %tmp37, i64 %tmp41
  %tmp43 = load i32, i32* %tmp42, align 4
  %tmp44 = load %struct.setup*, %struct.setup** %tmp1, align 8
  %tmp45 = getelementptr inbounds %struct.setup, %struct.setup* %tmp44, i32 0, i32 0
  %tmp46 = load i32*, i32** %tmp45, align 8
  %tmp47 = load i32, i32* %tmp46, align 4
  %tmp48 = or i32 %tmp47, %tmp43
  store i32 %tmp48, i32* %tmp46, align 4
  %tmp49 = load %struct.setup*, %struct.setup** %tmp1, align 8
  %tmp50 = getelementptr inbounds %struct.setup, %struct.setup* %tmp49, i32 0, i32 1
  %tmp51 = load i32*, i32** %tmp50, align 8
  %tmp52 = load %struct.setup*, %struct.setup** %tmp1, align 8
  %tmp53 = getelementptr inbounds %struct.setup, %struct.setup* %tmp52, i32 0, i32 2
  %tmp54 = load i32, i32* %tmp53, align 8
  %tmp55 = sext i32 %tmp54 to i64
  %tmp56 = getelementptr inbounds i32, i32* %tmp51, i64 %tmp55
  store i32 0, i32* %tmp56, align 4
  br label %bb57

bb57:                                             ; preds = %bb34, %bb11
  br label %bb5

bb58:                                             ; preds = %bb5
  ret i8* null
}

declare i32 @rand() #1

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define void @main() #0 {
bb:
  %tmp = alloca [2 x i32], align 4
  %tmp1 = alloca [2 x i32], align 4
  %tmp2 = alloca [2 x i64], align 16
  %tmp3 = alloca [2 x %struct.setup], align 16
  %tmp4 = alloca i32, align 4
  %tmp5 = alloca i32, align 4
  %tmp6 = alloca i32, align 4
  %tmp7 = alloca i32, align 4
  store i32 0, i32* %tmp4, align 4
  br label %bb8

bb8:                                              ; preds = %bb42, %bb
  %tmp9 = load i32, i32* %tmp4, align 4
  %tmp10 = icmp slt i32 %tmp9, 2
  br i1 %tmp10, label %bb11, label %bb45

bb11:                                             ; preds = %bb8
  %tmp12 = load i32, i32* %tmp4, align 4
  %tmp13 = shl i32 1, %tmp12
  %tmp14 = load i32, i32* %tmp4, align 4
  %tmp15 = sext i32 %tmp14 to i64
  %tmp16 = getelementptr inbounds [2 x i32], [2 x i32]* %tmp, i64 0, i64 %tmp15
  store i32 %tmp13, i32* %tmp16, align 4
  %tmp17 = load i32, i32* %tmp4, align 4
  %tmp18 = sext i32 %tmp17 to i64
  %tmp19 = getelementptr inbounds [2 x i32], [2 x i32]* %tmp1, i64 0, i64 %tmp18
  store i32 0, i32* %tmp19, align 4
  %tmp20 = load i32, i32* %tmp4, align 4
  %tmp21 = sext i32 %tmp20 to i64
  %tmp22 = getelementptr inbounds [2 x i32], [2 x i32]* %tmp, i64 0, i64 %tmp21
  %tmp23 = load i32, i32* %tmp4, align 4
  %tmp24 = sext i32 %tmp23 to i64
  %tmp25 = getelementptr inbounds [2 x %struct.setup], [2 x %struct.setup]* %tmp3, i64 0, i64 %tmp24
  %tmp26 = getelementptr inbounds %struct.setup, %struct.setup* %tmp25, i32 0, i32 0
  store i32* %tmp22, i32** %tmp26, align 8
  %tmp27 = getelementptr inbounds [2 x i32], [2 x i32]* %tmp1, i32 0, i32 0
  %tmp28 = load i32, i32* %tmp4, align 4
  %tmp29 = sext i32 %tmp28 to i64
  %tmp30 = getelementptr inbounds [2 x %struct.setup], [2 x %struct.setup]* %tmp3, i64 0, i64 %tmp29
  %tmp31 = getelementptr inbounds %struct.setup, %struct.setup* %tmp30, i32 0, i32 1
  store i32* %tmp27, i32** %tmp31, align 8
  %tmp32 = load i32, i32* %tmp4, align 4
  %tmp33 = load i32, i32* %tmp4, align 4
  %tmp34 = sext i32 %tmp33 to i64
  %tmp35 = getelementptr inbounds [2 x %struct.setup], [2 x %struct.setup]* %tmp3, i64 0, i64 %tmp34
  %tmp36 = getelementptr inbounds %struct.setup, %struct.setup* %tmp35, i32 0, i32 2
  store i32 %tmp32, i32* %tmp36, align 8
  %tmp37 = load i32, i32* %tmp4, align 4
  %tmp38 = sext i32 %tmp37 to i64
  %tmp39 = getelementptr inbounds [2 x i32], [2 x i32]* %tmp, i64 0, i64 %tmp38
  %tmp40 = load i32, i32* %tmp39, align 4
  %tmp41 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.str, i32 0, i32 0), i32 %tmp40) #2
  br label %bb42

bb42:                                             ; preds = %bb11
  %tmp43 = load i32, i32* %tmp4, align 4
  %tmp44 = add nsw i32 %tmp43, 1
  store i32 %tmp44, i32* %tmp4, align 4
  br label %bb8

bb45:                                             ; preds = %bb8
  store i32 0, i32* %tmp5, align 4
  br label %bb46

bb46:                                             ; preds = %bb58, %bb45
  %tmp47 = load i32, i32* %tmp5, align 4
  %tmp48 = icmp slt i32 %tmp47, 2
  br i1 %tmp48, label %bb49, label %bb61

bb49:                                             ; preds = %bb46
  %tmp50 = load i32, i32* %tmp5, align 4
  %tmp51 = sext i32 %tmp50 to i64
  %tmp52 = getelementptr inbounds [2 x i64], [2 x i64]* %tmp2, i64 0, i64 %tmp51
  %tmp53 = load i32, i32* %tmp5, align 4
  %tmp54 = sext i32 %tmp53 to i64
  %tmp55 = getelementptr inbounds [2 x %struct.setup], [2 x %struct.setup]* %tmp3, i64 0, i64 %tmp54
  %tmp56 = bitcast %struct.setup* %tmp55 to i8*
  %tmp57 = call i32 @pthread_create(i64* %tmp52, %struct.pthread_attr_t* null, i8* (i8*)* @protocol, i8* %tmp56) #2
  br label %bb58

bb58:                                             ; preds = %bb49
  %tmp59 = load i32, i32* %tmp5, align 4
  %tmp60 = add nsw i32 %tmp59, 1
  store i32 %tmp60, i32* %tmp5, align 4
  br label %bb46

bb61:                                             ; preds = %bb46
  store i32 0, i32* %tmp6, align 4
  br label %bb62

bb62:                                             ; preds = %bb71, %bb61
  %tmp63 = load i32, i32* %tmp6, align 4
  %tmp64 = icmp slt i32 %tmp63, 2
  br i1 %tmp64, label %bb65, label %bb74

bb65:                                             ; preds = %bb62
  %tmp66 = load i32, i32* %tmp6, align 4
  %tmp67 = sext i32 %tmp66 to i64
  %tmp68 = getelementptr inbounds [2 x i64], [2 x i64]* %tmp2, i64 0, i64 %tmp67
  %tmp69 = load i64, i64* %tmp68, align 8
  %tmp70 = call i32 @pthread_join(i64 %tmp69, i8** null) #2
  br label %bb71

bb71:                                             ; preds = %bb65
  %tmp72 = load i32, i32* %tmp6, align 4
  %tmp73 = add nsw i32 %tmp72, 1
  store i32 %tmp73, i32* %tmp6, align 4
  br label %bb62

bb74:                                             ; preds = %bb62
  store i32 0, i32* %tmp7, align 4
  br label %bb75

bb75:                                             ; preds = %bb87, %bb74
  %tmp76 = load i32, i32* %tmp7, align 4
  %tmp77 = icmp slt i32 %tmp76, 2
  br i1 %tmp77, label %bb78, label %bb90

bb78:                                             ; preds = %bb75
  %tmp79 = load i32, i32* %tmp7, align 4
  %tmp80 = sext i32 %tmp79 to i64
  %tmp81 = getelementptr inbounds [2 x i32], [2 x i32]* %tmp, i64 0, i64 %tmp80
  %tmp82 = load i32, i32* %tmp81, align 4
  %tmp83 = icmp ne i32 %tmp82, 3
  br i1 %tmp83, label %bb84, label %bb86

bb84:                                             ; preds = %bb78
  %tmp85 = call i32 @VERIFIERError() #2
  br label %bb86

bb86:                                             ; preds = %bb84, %bb78
  br label %bb87

bb87:                                             ; preds = %bb86
  %tmp88 = load i32, i32* %tmp7, align 4
  %tmp89 = add nsw i32 %tmp88, 1
  store i32 %tmp89, i32* %tmp7, align 4
  br label %bb75

bb90:                                             ; preds = %bb75
  call void @VERIFIERFinished() #2
  ret void
}

declare i32 @printf(i8*, ...) #1

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
