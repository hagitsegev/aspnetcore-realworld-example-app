def doCheckout (String gitURL, String branch, String checkoutDir = WORKSPACE) {
	println "git.doCheckout: Checkout git repo: |" + gitURL + "| branch: |$branch|  dir: |$checkoutDir|, workspace: |$WORKSPACE|"
    boolean dirExists = fileExists checkoutDir
	if (! dirExists) {
		echo "Creating dir |${checkoutDir}| for checking out"
		sh "mkdir ${checkoutDir}"
	}

	checkout([
		$class: 'GitSCM',
		branches: [[name: branch]],
		extensions: [[
			$class: 'SubmoduleOption',
			disableSubmodules: false,
			parentCredentials: true,
			recursiveSubmodules: true,
			timeout: 60,
			trackingSubmodules: false
		]],
		submoduleCfg: [],
		userRemoteConfigs: [[
			url: gitURL,
			refspec: "+refs/heads/$branch:refs/remotes/origin/$branch",
			credentialsId: 'hagitsegev'
		]]
	])
}

return this
