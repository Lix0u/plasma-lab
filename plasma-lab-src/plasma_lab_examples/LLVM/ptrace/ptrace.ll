; ModuleID = '/tmp/out.ll'
source_filename = "ptrace/ptrace.c"
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@SIGHUP = constant i32 1, align 4
@SIGINT = constant i32 2, align 4
@SIGQUIT = constant i32 3, align 4
@SIGILL = constant i32 4, align 4
@SIGTRAP = constant i32 5, align 4
@SIGABRT = constant i32 6, align 4
@SIGIOT = constant i32 6, align 4
@SIGBUS = constant i32 7, align 4
@SIGFPE = constant i32 8, align 4
@SIGKILL = constant i32 9, align 4
@SIGUSR1 = constant i32 10, align 4
@SIGSEGV = constant i32 11, align 4
@SIGUSR2 = constant i32 12, align 4
@SIGPIPE = constant i32 13, align 4
@SIGALRM = constant i32 14, align 4
@SIGTERM = constant i32 15, align 4
@SIGSTKFLT = constant i32 16, align 4
@SIGCHLD = constant i32 17, align 4
@SIGCONT = constant i32 18, align 4
@SIGSTOP = constant i32 19, align 4
@SIGTSTP = constant i32 20, align 4
@SIGTTIN = constant i32 21, align 4
@SIGTTOU = constant i32 22, align 4
@PTRACE_ATTACH = constant i32 1, align 4
@PTRACE_DETACH = constant i32 2, align 4
@PTRACE_SYSCALL = constant i32 3, align 4
@c = global i32 0, align 4

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @syscaller() #0 {
bb:
  %tmp = alloca i32, align 4
  call void (...) @syscall() #2
  call void (...) @syscallend() #2
  %tmp1 = load i32, i32* %tmp, align 4
  ret i32 %tmp1
}

declare void @syscall(...) #1

declare void @syscallend(...) #1

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define void @handler(i32 %arg) #0 {
bb:
  %tmp = alloca i32, align 4
  store i32 %arg, i32* %tmp, align 4
  %tmp1 = load i32, i32* @c, align 4
  %tmp2 = add nsw i32 %tmp1, 1
  store i32 %tmp2, i32* @c, align 4
  ret void
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define i32 @ptracer() #0 {
bb:
  %tmp = alloca i32, align 4
  call void @signal(i32 17, void (i32)* @handler) #2
  %tmp1 = call i64 @ptrace(i32 1, i64 0) #2
  br label %bb2

bb2:                                              ; preds = %bb5, %bb
  %tmp3 = load i32, i32* @c, align 4
  %tmp4 = icmp slt i32 %tmp3, 1
  br i1 %tmp4, label %bb5, label %bb6

bb5:                                              ; preds = %bb2
  br label %bb2

bb6:                                              ; preds = %bb2
  %tmp7 = call i64 @ptrace(i32 3, i64 0) #2
  br label %bb8

bb8:                                              ; preds = %bb11, %bb6
  %tmp9 = load i32, i32* @c, align 4
  %tmp10 = icmp slt i32 %tmp9, 2
  br i1 %tmp10, label %bb11, label %bb12

bb11:                                             ; preds = %bb8
  br label %bb8

bb12:                                             ; preds = %bb8
  call void (...) @poketext() #2
  %tmp13 = call i64 @ptrace(i32 2, i64 0) #2
  %tmp14 = load i32, i32* %tmp, align 4
  ret i32 %tmp14
}

declare void @signal(i32, void (i32)*) #1

declare i64 @ptrace(i32, i64) #1

declare void @poketext(...) #1

attributes #0 = { noinline nounwind optnone sspstrong uwtable "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-jump-tables"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #2 = { nobuiltin }

!llvm.module.flags = !{!0, !1, !2}
!llvm.ident = !{!3}

!0 = !{i32 1, !"wchar_size", i32 4}
!1 = !{i32 7, !"PIC Level", i32 2}
!2 = !{i32 7, !"PIE Level", i32 2}
!3 = !{!"clang version 5.0.0 (tags/RELEASE_500/final)"}
