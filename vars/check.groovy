static def boolean isSnapshot(version) {
  // if version is not numeric and version does not contain 'RELEASE' return true, else false
  return (!version.isNumber() && !version.contains('RELEASE'))
}

static def boolean isRelease(version) {
  // if version is numeric or contains 'RELEASE' then return true, else false
  return (version.isNumber() || version.contains('RELEASE'))
}

static def boolean isReleaseCandidate(branch) {
  // if branch contains 'RC' then return true, else false
  return branch.contains('RC')
}

static def boolean skipPipeline(workingDirecory) {
  // if commitMessage contains 'skip pipeline' then true, else false
  // https://jenkins.io/doc/book/managing/script-approval/
  def proc = "git -C ${workingDirecory} --no-pager log -1 --pretty=%B".execute()
  proc.waitFor()
  return proc.in.text.contains('skip pipeline')
}
