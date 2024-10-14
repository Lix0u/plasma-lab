; ModuleID = '/tmp/out.ll'
source_filename = "fib/fib_bench_false-unreach-call.c"
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

%struct.pthread_attr_t = type { i32 }

@i = global i32 1, align 4
@j = global i32 1, align 4

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i8* @t1(i8* %arg) #0 {
bb:
  %tmp = alloca i8*, align 8
  %tmp1 = alloca i8*, align 8
  %tmp2 = alloca i32, align 4
  store i8* %arg, i8** %tmp1, align 8
  store i32 0, i32* %tmp2, align 4
  store i32 0, i32* %tmp2, align 4
  br label %bb3

bb3:                                              ; preds = %bb10, %bb
  %tmp4 = load i32, i32* %tmp2, align 4
  %tmp5 = icmp slt i32 %tmp4, 8
  br i1 %tmp5, label %bb6, label %bb13

bb6:                                              ; preds = %bb3
  %tmp7 = load i32, i32* @j, align 4
  %tmp8 = load i32, i32* @i, align 4
  %tmp9 = add nsw i32 %tmp8, %tmp7
  store i32 %tmp9, i32* @i, align 4
  br label %bb10

bb10:                                             ; preds = %bb6
  %tmp11 = load i32, i32* %tmp2, align 4
  %tmp12 = add nsw i32 %tmp11, 1
  store i32 %tmp12, i32* %tmp2, align 4
  br label %bb3

bb13:                                             ; preds = %bb3
  call void @pthread_exit(i8* null) #3
  %tmp14 = load i8*, i8** %tmp, align 8
  ret i8* %tmp14
}

declare void @pthread_exit(i8*) #1

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i8* @t2(i8* %arg) #0 {
bb:
  %tmp = alloca i8*, align 8
  %tmp1 = alloca i8*, align 8
  %tmp2 = alloca i32, align 4
  store i8* %arg, i8** %tmp1, align 8
  store i32 0, i32* %tmp2, align 4
  store i32 0, i32* %tmp2, align 4
  br label %bb3

bb3:                                              ; preds = %bb10, %bb
  %tmp4 = load i32, i32* %tmp2, align 4
  %tmp5 = icmp slt i32 %tmp4, 8
  br i1 %tmp5, label %bb6, label %bb13

bb6:                                              ; preds = %bb3
  %tmp7 = load i32, i32* @i, align 4
  %tmp8 = load i32, i32* @j, align 4
  %tmp9 = add nsw i32 %tmp8, %tmp7
  store i32 %tmp9, i32* @j, align 4
  br label %bb10

bb10:                                             ; preds = %bb6
  %tmp11 = load i32, i32* %tmp2, align 4
  %tmp12 = add nsw i32 %tmp11, 1
  store i32 %tmp12, i32* %tmp2, align 4
  br label %bb3

bb13:                                             ; preds = %bb3
  call void @pthread_exit(i8* null) #3
  %tmp14 = load i8*, i8** %tmp, align 8
  ret i8* %tmp14
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @main(i32 %arg, i8** %arg1) #0 {
bb:
  %tmp = alloca i32, align 4
  %tmp2 = alloca i8**, align 8
  %tmp3 = alloca i64, align 8
  %tmp4 = alloca i64, align 8
  store i32 %arg, i32* %tmp, align 4
  store i8** %arg1, i8*** %tmp2, align 8
  %tmp5 = call i32 @pthread_create(i64* %tmp3, %struct.pthread_attr_t* null, i8* (i8*)* @t1, i8* null) #3
  %tmp6 = call i32 @pthread_create(i64* %tmp4, %struct.pthread_attr_t* null, i8* (i8*)* @t2, i8* null) #3
  %tmp7 = load i64, i64* %tmp3, align 8
  %tmp8 = call i32 @pthread_join(i64 %tmp7, i8** null) #3
  %tmp9 = load i64, i64* %tmp4, align 8
  %tmp10 = call i32 @pthread_join(i64 %tmp9, i8** null) #3
  %tmp11 = load i32, i32* @i, align 4
  %tmp12 = icmp eq i32 %tmp11, 987
  br i1 %tmp12, label %bb16, label %bb13

bb13:                                             ; preds = %bb
  %tmp14 = load i32, i32* @j, align 4
  %tmp15 = icmp eq i32 %tmp14, 987
  br i1 %tmp15, label %bb16, label %bb18

bb16:                                             ; preds = %bb13, %bb
  br label %bb17

bb17:                                             ; preds = %bb16
  call void (...) @VERIFIERError() #4
  unreachable

bb18:                                             ; preds = %bb13
  ret i32 0
}

declare i32 @pthread_create(i64*, %struct.pthread_attr_t*, i8* (i8*)*, i8*) #1

declare i32 @pthread_join(i64, i8**) #1

; Function Attrs: noreturn
declare void @VERIFIERError(...) #2

attributes #0 = { noinline nounwind optnone sspstrong uwtable "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-jump-tables"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #2 = { noreturn "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #3 = { nobuiltin }
attributes #4 = { nobuiltin noreturn }

!llvm.module.flags = !{!0, !1, !2}
!llvm.ident = !{!3}

!0 = !{i32 1, !"wchar_size", i32 4}
!1 = !{i32 7, !"PIC Level", i32 2}
!2 = !{i32 7, !"PIE Level", i32 2}
!3 = !{!"clang version 5.0.0 (tags/RELEASE_500/final)"}
